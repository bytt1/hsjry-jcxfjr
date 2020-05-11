package data.publicdata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.Okhttp;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static utils.Okhttp.*;


public final class PublicFunc {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final Logger log = LoggerFactory.getLogger(PublicFunc.class);
    private static final String url = DOMAIN + "/v1/message/sendByMobile";
    private static final String GET_VERIFY_CODE_URL = "https://kibana-dev.corp.jccfc.com/elasticsearch/_msearch?rest_total_hits_as_int=true&ignore_throttled=true";
    private static final String RISK_CALLBACK_URL = DOMAIN + "/v1/risk/riskFirstCallBack";
    private static final String SEC_RICK_MNG_URL = DOMAIN + "/v1/risk/riskSecondCallBack";
    private static final String MAIN_MOBILE_NO = Okhttp.getPhone();
    private static final String DATABASE = "dev_credit";

    public static JSONObject getRealNameData() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doGet("http://10.10.61.77:8081/getTestData"));
    }

    public static String getUserId(String mobileNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
                " station_id = (SELECT station_id FROM dev_user.user_telephone_info WHERE" +
                " telephone = \""+mobileNo+"\" LIMIT 1)\n";
        return SqlUtils.select(sql);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(getUserId("13744421796"));
    }

    public static String getDatabase(){
        return  DATABASE;
    }

    public static String getMobileNo(){
        return MAIN_MOBILE_NO;
//        return "13711775766";
    }

    public static String getCertNo(){
        return "51102219900101" + fourRandom();
    }

    public static String getProductID(){
        return "F0220";
    }

    public static String getSpouseMobileNo(){
        return Okhttp.getPhone();
    }



    private static JSONObject callbackData(String serialNo) throws IOException {
        JSONObject customPolicyResult = new JSONObject();
        customPolicyResult.put("initLimith",null);
        customPolicyResult.put("pbocWorkunit","");

        JSONObject policyResult = new JSONObject();
        policyResult.put("seqId",serialNo);
        policyResult.put("finalDecision","Accept");
        policyResult.put("finalScore","100");
        policyResult.put("finalDealType","");
        policyResult.put("finalDealTypeName","");
        policyResult.put("spendTime","10");
        policyResult.put("customPolicyResult",customPolicyResult);

        JSONObject body = new JSONObject();
        body.put("code","0000");
        body.put("desc","审批通过");
        body.put("policyResult",policyResult);


        return body;
    }


    public static JSONObject firRiskMngCallback(String serialNo) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(RISK_CALLBACK_URL,
                Okhttp.requestBody(callbackData(serialNo)).toJSONString()));
    }

    public static JSONObject secRiskMngCallback(String serialNo) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(SEC_RICK_MNG_URL,
                Okhttp.requestBody(callbackData(serialNo)).toJSONString()));
    }


    public static String getCode(String env,String mobileNo) throws IOException {
        sendIdentityCode("001",mobileNo);
        String code;
        Response response;
        while (true){
            response = Okhttp.doTextPost(GET_VERIFY_CODE_URL,
                    getVerifyCodeData(env,mobileNo));
            long length =  Objects.requireNonNull(response.body()).contentLength();
            log.info("日志系统响应码长度 >>> " + length);
            if (length == -1){
                code = Okhttp.regExpResp(response);
                if (!"".equals(code) && null != code){
                    break;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return code;
    }



    private static String getVerifyCodeData(String env,String mobileNo){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str =  "{\"index\":\""+env+"\",\"ignore_unavailable\":true,\"preference\":1587006838391}\n{\"version\":true,\"size\":500,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"3h\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestamp\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"range\":{\"@timestamp\":{\"format\":\"strict_date_optional_time\",\"gte\":\""+format.format(new Date())+"T00:00:00.000Z\",\"lte\":\""+format.format(new Date())+"T23:59:59.999Z\"}}}],\"filter\":[{\"multi_match\":{\"type\":\"phrase\",\"query\":\""+mobileNo+"\",\"lenient\":true}}],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647},\"timeout\":\"30000ms\"}\n";
        log.info("请求数据 >>> " + str);
        return str;
    }




    public static JSONObject dealRequestHead(){

        JSONObject json = new JSONObject();
        json.put("tenantId","000");      // 必传                  租户id
        json.put("channelNo","06");       // 必传                 渠道编号
//        json.put("marketClue","01"); // 营销线索 01app 02支付宝 03门户 04微信 06管理台 07商户
        json.put("merchantId",Okhttp.getPropertiesVal("merchantId")); // 条件必传  （交易发起渠道为商户类渠道时不能为空）  商户编号
        json.put("organId","001"); // 机构编号
        json.put("empNo","00001"); // 条件必传   （交易发起渠道的接入方式
                                        // 为后台直联时不能为空，
                                        // 接入方式为页面调用时仅登录类交易不能为空）  客户经理编号
        json.put("token", getProVal("token")); // 条件必传   （） 登录授权码
        json.put("requestSerialNo",Okhttp.getUUid());     // 必传             全局唯一流水号
        json.put("requestTime",Okhttp.getCurrentDateTime());     // 必传           请求日期
//        json.put("rebackUrl",Okhttp.getPropertiesVal("fileUrl")); // 前端页面回调url
//        json.put("notifyUrl",Okhttp.getPropertiesVal("fileUrl")); // 异步通知回调
       // json.put("deviceType","ipad"); //设备类型ipad
        json.put("deviceId",Okhttp.eightNum()); //设备标识
        json.put("longitude","100.23412"); // 条件必传（涉及GPS计算的接口不能为空） GPS 经度
        json.put("latitude","39.12323"); // 条件必传（涉及GPS计算的接口不能为空） GPS 纬度
//        json.put("netIp","110.225.1.10"); // 请求ip地址
//        json.put("macAddr","F8-59-71-46-C1-B6"); // 请求mac地址

        return json;
    }

    public static JSONObject notificationHead(JSONObject json){
        JSONObject notification = new JSONObject();
        notification.put("tenantId","000");    // 必传
        notification.put("channelNo","06");    // 必传
        notification.put("merchantId","");    //
        notification.put("serialNo",Okhttp.getUUid());    // 必传  交易流水
        notification.put("transTime",Okhttp.getCurrentDateTime());    // 必传
        notification.put("transStatus","");    // 必传 交易状态
        notification.put("returnMessage","");    // 必传
        notification.put("requestSerialNo","");    // 必传
        notification.put("requestTime","");    //  必传

        return notification;
    }


    /**
     * @param type 001 - 手机号码注册：仅支持未注册的手机号码；
     *             002 - 短信验证码登录：仅支持已注册的手机号码；
     *             003 - 客户登录密码重置（忘记登录密码）：仅支持已注册的手机号码；
     * **/
    private static void sendIdentityCode(String type,String phoneNumber){
        JSONObject body = new JSONObject();
        body.put("smsType",type);//  必传  业务类型   	001 - 手机号码注册：仅支持未注册的手机号码；
                                            // 	002 - 短信验证码登录：仅支持已注册的手机号码；
                                           // 	003 - 客户登录密码重置（忘记登录密码）：仅支持已注册的手机号码；
        body.put("mobileNo",phoneNumber);// 必传  手机号码

        try {
            Okhttp.analysisToStr(Okhttp.doPost(url,
                    Okhttp.requestBody(body).toJSONString()));
            log.info("验证码发送成功 !!!");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static JSONObject interfacePublicField(String userId){
        JSONObject field = new JSONObject();
        field.put("userId",userId);
        field.put("thirdUserId","");

        return field;
    }

    public static void respAssert(JSONObject jsonObject){
        String assertText = jsonObject.getJSONObject("head").getString("returnMessage");
        if ("成功".equals(assertText)){
            Assert.assertTrue(true);
        }else {
            Assert.fail(assertText);
        }
    }

    //文件
    public static JSONArray attachments(){
        JSONObject face = new JSONObject();
        face.put("fileKind",1); // 必传
        face.put("fileUrl", Okhttp.getPropertiesVal("faceFileUrl")); // 必传
        face.put("fileName","身份证正面"); // 必传

        JSONObject back = new JSONObject();
        back.put("fileKind",2); // 必传
        back.put("fileUrl", Okhttp.getPropertiesVal("backFileUrl")); // 必传
        back.put("fileName","身份证反面"); // 必传
        JSONArray array = new JSONArray();
        array.add(face);
        array.add(back);

        return array;
    }

    //主借人补充资料
    public static JSONArray staySupplementsInfo(String relationType){
        JSONObject indexOne = new JSONObject();
        indexOne.put("fileKind",46); // 必传
        indexOne.put("fileUrl", Okhttp.getPropertiesVal("faceFileUrl")); // 必传
        indexOne.put("fileName","身份证反面"); // 必传

        JSONObject indexTwo = new JSONObject();
        indexTwo.put("fileKind",108); // 必传
        indexTwo.put("fileUrl", Okhttp.getPropertiesVal("backFileUrl")); // 必传
        indexTwo.put("fileName","身份证反面"); // 必传

        JSONObject indexThree = new JSONObject();
        indexThree.put("fileKind",104); // 必传
        indexThree.put("fileUrl", Okhttp.getPropertiesVal("backFileUrl")); // 必传
        indexThree.put("fileName","身份证反面"); // 必传

        JSONObject indexFour = new JSONObject();
        indexFour.put("fileKind",8); // 必传
        indexFour.put("fileUrl", Okhttp.getPropertiesVal("backFileUrl")); // 必传
        indexFour.put("fileName","身份证反面"); // 必传

        JSONObject indexFive = new JSONObject();
        indexOne.put("fileKind",105); // 必传
        indexOne.put("fileUrl", Okhttp.getPropertiesVal("faceFileUrl")); // 必传
        indexOne.put("fileName","身份证反面"); // 必传

        JSONArray array = new JSONArray();

        if ("main".equals(relationType)){
            array.add(indexTwo);
            array.add(indexThree);
            array.add(indexFour);
            array.add(indexFive);
        }else if ("common".equals(relationType)){
            array.add(indexOne);
            array.add(indexTwo);
        }else {
            try {
                throw new Exception("relationType as main/common");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        array.add(indexOne);


        return array;
    }


    //合同
    public static JSONArray contract(){
        JSONObject contract = new JSONObject();

        contract.put("compactId","4199769758219567585");
        contract.put("compactType","90"); //必传

        JSONArray array = new JSONArray();
        array.add(contract);

        return array;
    }

    public static JSONArray loanContract(){
        JSONObject contract = new JSONObject();

        contract.put("compactId","4199769758219567585");
        contract.put("compactType","8"); //必传

        JSONArray array = new JSONArray();
        array.add(contract);

        return array;
    }



    //联系人列表
    public static JSONArray correlationPerList(){
        JSONObject indexOne = new JSONObject();
        indexOne.put("affiliatedType","03");
        indexOne.put("userName","隔壁");
        indexOne.put("telphoneNo",getPhone());
        indexOne.put("contactsInformed","1");

        JSONObject indexTwo = new JSONObject();
        indexTwo.put("affiliatedType","08");
        indexTwo.put("userName","老王");
        indexTwo.put("telphoneNo",getPhone());
        indexTwo.put("contactsInformed","1");

        JSONArray array = new JSONArray();
        array.add(indexOne);
        array.add(indexTwo);

        return array;
    }

    //扩展信息列表
    public static JSONObject extendInfoList(){
        JSONObject list = new JSONObject();
        list.put("companyPhone","02148888");
        return list;
    }

}
