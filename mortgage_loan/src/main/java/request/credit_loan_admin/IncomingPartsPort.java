package request.credit_loan_admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.credit_loan_admin.IncomingPartsData;
import data.publicdata.PublicFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import utils.Okhttp;

import java.io.*;
import java.sql.SQLException;


import static utils.Okhttp.*;

public class IncomingPartsPort {

    private static final Logger log = LoggerFactory.getLogger(IncomingPartsPort.class);
    private static final String DOMAIN = Okhttp.getDomain();
    private static final String SCHEDULE_QUERY_URL = DOMAIN + "/v1/credit/queryLoanProcess";
    private static final String PRE_CREDIT_APPLY_SAVE_URL = DOMAIN + "/v1/credit/preCreditApplySave";
    private static final String HOUSING_INFO_MANAGE_URL = DOMAIN + "/v1/credit/houseInfoManage";
    private static final String MOR_CORR_INFO_MNG_QUERY_URL = DOMAIN + "/v1/credit/queryMortgageHouseInfo";
    private static final String REG_MARRIAGE_INFO_URL = DOMAIN + "/v1/credit/marriageInfoManager";
    private static final String CORR_INFO_QUERY_URL = DOMAIN + "/v1/credit/queryAffiliatedInfoList";
    private static final String ADD_CORRELATION_PERSON_URL = DOMAIN + "/v1/credit/addAffiliatedInfo";
    private static final String EDIT_CORR_PER_INFO_URL = DOMAIN + "/v1/credit/modifyAffiliatedInfo";
    private static final String DEL_CORR_PER_URL = DOMAIN + "/v1/credit/deleteAffiliatedInfo";
    private static final String CMT_PRE_CREDIT_INFO_URL = DOMAIN + "/v1/credit/commitPreCreditInfo";
    private static final String FIR_MORT_INFO_LIST_QUERY_URL = DOMAIN + "/v1/credit/queryFirstMortgage";
    private static final String OTH_CORR_PER_AUTH_QUE_URL = DOMAIN + "/v1/credit/queryWaitAuthorityList";
    private static final String AFT_CORR_AUTH_CMT_CDT_URL = DOMAIN + "/v1/credit/preCreditSubmitApplyAftAllAuth";
    private static final String STAY_SUPP_ACC_LST_QUY_URL = DOMAIN + "/v1/credit/queryWaitUploadAccessoryList";
    private static final String SUPP_INFO_CMT_URL = DOMAIN + "/v1/credit/commitSupplement";
    private static final String CRT_CHOICE_DEADLINE_QUERY_URL = DOMAIN + "/v1/product/queryLoanTermUnit";




    //共同借款进度查询
    public static JSONObject coBorrowScheduleQuery() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(SCHEDULE_QUERY_URL,
                Okhttp.requestBody(IncomingPartsData.coBorApplyScheduleQuery()).toJSONString()));
    }


    //预授信保存
    public static JSONObject preCreditApplySave(JSONObject identityDiscernResp,
                                                String mainCreditApplyId,
                                                String userId) throws IOException {

        return Okhttp.analysisToJson(Okhttp.doPost(PRE_CREDIT_APPLY_SAVE_URL,
                Okhttp.requestBody(IncomingPartsData.preCreditApplySave(identityDiscernResp,
                        mainCreditApplyId,userId)).toJSONString()));
    }

    //房产信息管理
    public static JSONObject houseInfoManagement(String creditApplyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(HOUSING_INFO_MANAGE_URL,
                Okhttp.requestBody(IncomingPartsData.houseInfoManager(creditApplyId)).toJSONString()));
    }

    //抵押相关信息管理查询
    public static JSONObject mortgageCorrelationInfoManagementQuery(String creditApplyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(MOR_CORR_INFO_MNG_QUERY_URL,
                Okhttp.requestBody(IncomingPartsData.morCorrInfoQuery(creditApplyId)).toJSONString()));
    }

    //登记婚姻信息
    public static JSONObject registerMarriageInfo(String creditApplyId,String spousePhone) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(REG_MARRIAGE_INFO_URL,
                Okhttp.requestBody(IncomingPartsData.regMarriageInfo(creditApplyId,spousePhone)).toJSONString()));
    }

    //关联人信息查询
    public static JSONObject correlationInfoQuery(String mainCreditApplyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(CORR_INFO_QUERY_URL,
                Okhttp.requestBody(IncomingPartsData.correlationInfoQuery(mainCreditApplyId)).toJSONString()));
    }

    //添加关联人
    public static JSONObject addCorrelationPerson(String mainCrtAlyId,String uid) throws IOException {
        JSONObject reqData = IncomingPartsData.addCorrelationPerson(mainCrtAlyId,uid);

        return Okhttp.analysisToJson(Okhttp.doPost(ADD_CORRELATION_PERSON_URL,
                Okhttp.requestBody(reqData).toJSONString()));
    }

    //修改关联人信息  提示关联人不存在
    public static JSONObject editCorrelationPersonInfo() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(EDIT_CORR_PER_INFO_URL,
                Okhttp.requestBody(IncomingPartsData.editCorrelationPersonInfo()).toJSONString()));
    }

    //删除关联人
    public static JSONObject deleteCorrelationPerson(String CreditApplyId,String corrId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(DEL_CORR_PER_URL,
                Okhttp.requestBody(IncomingPartsData.deleteCorrelationPerson(CreditApplyId,corrId)).toJSONString()));
    }

    //提交预授信信息
    public static JSONObject commitPredictCreditInfo(String creditApplyId,float applyAmount,
                                                     String loanPeriod) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(CMT_PRE_CREDIT_INFO_URL,
                Okhttp.requestBody(IncomingPartsData
                        .commitPreCreditInfo(creditApplyId,applyAmount,loanPeriod)).toJSONString()));
    }

    //一抵信息列表查询
    public static JSONObject firstMortgageInfoListQuery(String productId,String mainCreditApplyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(FIR_MORT_INFO_LIST_QUERY_URL,
                Okhttp.requestBody(IncomingPartsData.firstMortgageInfoListQuery(productId,
                        mainCreditApplyId))
                        .toJSONString()));
    }

    //其他关联人授信列表查询  未完成  body null
    public static JSONObject otherCorrelationPersonAuthQuery(String productId,String mainCreditApplyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(OTH_CORR_PER_AUTH_QUE_URL,
                Okhttp.requestBody(IncomingPartsData.otherCorrelationPersonListQuery(
                        productId,mainCreditApplyId
                ))
                        .toJSONString()));
    }

    //关联人全部授权后体检授信
    public static JSONObject afterAllCorrelationAuthCommitCredit(String productId,
                                                                 String mainCreditApplyId,
                                                                 String userName) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(AFT_CORR_AUTH_CMT_CDT_URL,
                Okhttp.requestBody(IncomingPartsData.corrPerAfterAuthCmtCredit(productId,
                        mainCreditApplyId,userName))
                        .toJSONString()));
    }

    //待补充附件列表查询
    public static JSONObject staySupplementAccessoryListQuery(String applyId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(STAY_SUPP_ACC_LST_QUY_URL,
                Okhttp.requestBody(IncomingPartsData.staySupplementedAccessoryListQuery(applyId))
                        .toJSONString()));
    }

    //补充资料提交
    public static JSONObject supplementInfoCommit(String productId,String creditApplyId,
                                                  JSONObject realName,String userId,String relationType) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(SUPP_INFO_CMT_URL,
                Okhttp.requestBody(IncomingPartsData.supplementInformationCmt(productId,creditApplyId,
                        realName,userId,relationType))
                        .toJSONString()));
    }

    //贷款可选期限查询
    public static JSONObject loanChoiceDeadlineQuery(String mainApplyId,String creditApplyId) throws IOException {
        JSONObject json = Okhttp.analysisToJson(Okhttp.doPost(CRT_CHOICE_DEADLINE_QUERY_URL,
                Okhttp.requestBody(IncomingPartsData.loanSelectableDeadlineQuery(mainApplyId,creditApplyId))
                        .toJSONString()));
        return json;
    }


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        JSONObject regInfoQuery = JSON.parseObject(getProVal("regInfoQuery"));
        JSONObject realName = JSON.parseObject(getProVal("realName"));


        //        coBorrowScheduleQuery();
//        preCreditApplySave(realName,true,"");
        JSONObject preInfo = JSON.parseObject(getProVal("preCreditApplySave"));

//        String sql = "select sequence_id from dev_credit.credit_risk_flow where biz_apply_id=\""+preInfo.getString("creditApplyId")+"\";";
//        String serial = SqlUtils.select(sql,"dev_credit");
//        PublicFunc.firRiskMngCallback(serial);

//        houseInfoManagement(preInfo.getString("creditApplyId"));

//        mortgageCorrelationInfoManagementQuery("000CA2019080000000005");
//        CustomRegister.verifyIdentityInfo(info);

//        String p = Okhttp.getPhone();
//        registerMarriageInfo(preInfo.getString("creditApplyId"),p,p);

//        addCorrelationPerson(info.getString("userName"),regInfoQuery.getString("certificateNo"),
//                preInfo.getString("mainCreditApplyId"),regInfoQuery.getString("userId"));

//        correlationInfoQuery(preInfo.getString("mainCreditApplyId"));


        loanChoiceDeadlineQuery("000CA2020040000077042","000CA2020040000077044");


        //        editCorrelationPersonInfo();
//        deleteCorrelationPerson("000CA2020040000000308","4235401631530705013");

        JSONObject array = JSON.parseObject(getProVal("loanChoiceDeadlineQuery"));
//                .getJSONArray("loanTermList");

//        commitPredictCreditInfo(preInfo.getString("creditApplyId"),10000.00f,array.getString(1));

//        firstMortgageInfoListQuery("F0220",preInfo.getString("mainCreditApplyId"));
//        JSONObject correlation = JSON.parseObject(getProVal("correlationPer"))
//                .getJSONArray("dataList").getJSONObject(0);
//
//        CustomAuthentication.corrFaceDiscern(correlation);
//        preCreditApplySave(realName,true,"");


//        otherCorrelationPersonAuthQuery("","000CA2019080000000005");


//        afterAllCorrelationAuthCommitCredit("F0220",
//                preInfo.getString("mainCreditApplyId"),
//                realName.getString("userName"));


//        String sql = "select sequence_id,create_time from dev_credit.credit_risk_flow where biz_apply_id=\""+preInfo.getString("creditApplyId")+"\";";
//        ResultSet resultSet = SqlUtils.selectAll(sql,"dev_credit");

//
//        staySupplementAccessoryListQuery("000CA2020040000076936");
//        String real = "{\"head\":{\"requestTime\":\"20200428093359\",\"channelNo\":\"06\",\"merchantId\":\"000UC010000850541\",\"latitude\":\"39.12323\",\"tenantId\":\"000\",\"organId\":\"001\",\"empNo\":\"00001\",\"deviceId\":\"75252040\",\"token\":\"CE987FE8049E4A8C97FA6D835C2AD60F\",\"requestSerialNo\":\"b7758f480ed242b2bc6f8307ae4ff402\",\"longitude\":\"100.23412\"},\"body\":{\"thirdUserId\":\"\",\"pmtAddress\":\"居厢遗鸳瓮钦\",\"certType\":\"0\",\"attachments\":[{\"fileName\":\"身份证正面\",\"fileKind\":1,\"fileUrl\":\"http://jccfc-dev.ks3-cn-beijing.ksyun.com/xdgl/ydhb/yw/wyrc/20200101/2020010208.jpg\"},{\"fileName\":\"身份证反面\",\"fileKind\":2,\"fileUrl\":\"http://jccfc-dev.ks3-cn-beijing.ksyun.com/xdgl/ydhb/yw/wyrc/20200101/2020010207.jpg\"}],\"gender\":2,\"pmtAddrProvinceCode\":\"230000\",\"nation\":\"汉\",\"dueDate\":\"20260915\",\"signOrgan\":\"成都市公安局\",\"pmtAddrAreaCode\":\"231025\",\"userName\":\"痞媚褒\",\"userId\":\"\",\"authMode\":0,\"certNo\":\"459325199810088636\",\"issueDate\":\"20060915\",\"pmtAddrCityCode\":\"231000\",\"certificateCheck\":\"1\"}}\n";
//
//        JSONObject name = JSON.parseObject(real);
//        supplementInfoCommit(PublicFunc.getProductID(),"000CA2020040000077256",name,
//                "000UC020000852313","main");
//        writerPro(realName,"realName",true);

//        correlationInfoQuery("000CA2020040000077001");

        mortgageCorrelationInfoManagementQuery("000CA2020040000077256");


    }

}
