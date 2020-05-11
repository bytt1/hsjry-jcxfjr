package incomingParts_flow_test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.*;
import utils.FileUtils;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static data.publicdata.PublicFunc.*;
import static data.publicdata.PublicFunc.respAssert;
import static data.publicdata.RealNameAuth.realNameData;
import static utils.Okhttp.*;
import static utils.Okhttp.getProVal;

public class MainSimpleIncomingPartsFlowTest {

    private final Logger log = LoggerFactory.getLogger(MainSimpleIncomingPartsFlowTest.class);
    private final String PRODUCT_ID = getProductID();
    private JSONObject json;
    private final String SPOUSE_PHONE = getSpouseMobileNo();
    private final String MAIN_MOBILE = getMobileNo();
    private String userID;
    private JSONObject preCreditApplySave;
    private JSONObject regInfoQuery;
    private JSONObject realName;
    private JSONObject realData;
    private String creditApplyId;
    private String sql = "select sequence_id,create_time from dev_credit.credit_risk_flow where biz_apply_id=";
    private JSONArray loanPeriod;
    private String firstRiskSeq;

    @Test
    public void login(){
        try {
            json = UserLogin.loginByAccount(MAIN_MOBILE,"123456");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void customRegister() {
        try {

            json = CustomRegister.register(MAIN_MOBILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    @Test
    public void customRegInfoQuery() {
        try {
            json = CustomQuery.regInfoQuery(MAIN_MOBILE);
            userID = json.getJSONObject("body").getString("userId");
            FileUtils.writerIdentityInfo("regInfoQuery", json.getJSONObject("body"));
            regInfoQuery = json.getJSONObject("body");

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void realNameAuth() {
        try {
            //data.getString("身份证号")
            JSONObject data = PublicFunc.getRealNameData();
            realName = realNameData(data.getString("姓名"),data.getString("身份证号")
                    , userID);
            FileUtils.writerIdentityInfo("实名认证", realName);
            json = CustomAuthentication.realNameAuth(realName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void setTradePassword(){
        try {
            json = PasswordAdmin.setTradePassword("245253");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void accountBind(){

        try {
            JSONObject data = PublicFunc.getRealNameData();
            json = AccountBinding.bankCardBind(realName.getString("userName"),realName.getString("certNo"),
                    data.getString("银行卡号"),
                    MAIN_MOBILE,"245253");
            FileUtils.writerIdentityInfo("数据",data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void accountQuery(){
        try {
            json = AccountBinding.bindAccountInfoQuery(userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void faceDiscern(){
        try {
            json = CustomAuthentication.faceDiscern(realName,userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void faceDiscernResultQuery(){

        try {
            json = CustomAuthentication.resultQuery(userID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        respAssert(json);

    }

    @Test
    public void preCreditApplySave() {
        try {
            String str = "{\"pmtAddress\":\"京仁秘涕绣衫\",\"certType\":\"0\",\"attachments\":[{\"fileName\":\"身份证正面\",\"fileKind\":1,\"fileUrl\":\"http://jccfc-dev.ks3-cn-beijing.ksyun.com/xdgl/ydhb/yw/wyrc/20200101/2020010208.jpg\"},{\"fileName\":\"身份证反面\",\"fileKind\":2,\"fileUrl\":\"http://jccfc-dev.ks3-cn-beijing.ksyun.com/xdgl/ydhb/yw/wyrc/20200101/2020010207.jpg\"}],\"gender\":1,\"pmtAddrProvinceCode\":\"230000\",\"nation\":\"汉\",\"dueDate\":\"20260915\",\"signOrgan\":\"成都市公安局\",\"pmtAddrAreaCode\":\"231025\",\"userName\":\"韩祥\",\"userId\":\"000UC020000853788\",\"authMode\":0,\"certNo\":\"422223195902224918\",\"issueDate\":\"20060915\",\"pmtAddrCityCode\":\"231000\",\"certificateCheck\":\"1\"}";
            realData = JSON.parseObject(str);
            json = IncomingPartsPort.preCreditApplySave(realName, "", userID);
            preCreditApplySave = json.getJSONObject("body");
            log.info("预授信保存响应报文 >>> " + preCreditApplySave);
            creditApplyId = preCreditApplySave.getString("creditApplyId");
            log.info("creditApplyId >>> " + creditApplyId);
            FileUtils.writerIdentityInfo("preCreditApplySave", preCreditApplySave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void firstRiskCallback() {

        try {
            firstRiskSeq = SqlUtils.select(
                    sql + "\"" + creditApplyId + "\" order by create_time desc limit 1;");

            log.info("一次风控回调sequence_id >>> " + firstRiskSeq);
            json = firRiskMngCallback(firstRiskSeq);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        respAssert(json);

    }

    @Test
    public void houseInfoMng() {
        try {
            json = IncomingPartsPort.houseInfoManagement(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void regMarriageInfo() {
        try {
            JSONObject data = PublicFunc.getRealNameData();
            log.info("配偶电话 >>> " + SPOUSE_PHONE);
            json = IncomingPartsPort.registerMarriageInfo(creditApplyId,data.getString("身份证号"),SPOUSE_PHONE, userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void addCorrelationPer() {
        try {
            JSONObject data = PublicFunc.getRealNameData();
            json = IncomingPartsPort.addCorrelationPerson(creditApplyId, data.getString("身份证号"),userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void loanChoiceDeadlineQuery() {
        try {
            json = IncomingPartsPort.loanChoiceDeadlineQuery(preCreditApplySave.getString("mainCreditApplyId"),
                    creditApplyId);
            loanPeriod = json.getJSONObject("body").getJSONArray("loanTermList");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void commitPredictCreditInfo() {
        try {
            json = IncomingPartsPort.commitPredictCreditInfo(creditApplyId,
                    200000.00f, loanPeriod.getString(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    //"registerMarriageInfo",
    @Test(dependsOnGroups = {"correlation"})
    public void afterAllCorrelationAuthCommitCredit() {
        try {
            json = IncomingPartsPort.afterAllCorrelationAuthCommitCredit(PRODUCT_ID,
                    preCreditApplySave.getString("mainCreditApplyId"),
                    realName.getString("userName"));
//                    realData.getString("userName"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test(dependsOnMethods = "afterAllCorrelationAuthCommitCredit")
    public void secondRiskMngCallback() {

        String seqId;
        String sql = "SELECT f.sequence_id FROM dev_credit.credit_risk_flow f where" +
                " f.biz_apply_id in (SELECT a.affiliated_apply_id FROM dev_credit.credit_affiliated_info" +
                " a WHERE main_borrower_id = (SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
                " station_id = (SELECT station_id FROM dev_user.user_telephone_info WHERE" +
                " telephone = \""+MAIN_MOBILE+"\" ORDER BY create_time DESC LIMIT 1))ORDER BY" +
                " a.affiliated_apply_id ASC)";

        log.info("主借人申请单号 >>> " + creditApplyId);

        try {

            ResultSet res = SqlUtils.selectAll(sql);

            while (res.next()) {
                seqId = res.getString(1);
                if (firstRiskSeq.equals(seqId)){
                    continue;
                }
                log.info("二次风控回调sequence_id >>> " + seqId);
                json = secRiskMngCallback(seqId);
                if (json.getJSONObject("head").getString("returnMessage").contains("幂等")) {
                    continue;
                } else if (json.getJSONObject("body").getString("msg").contains("处理异常")) {
                    log.info("异常风控流水 >>> " + seqId);
                }
                respAssert(json);
            }


        } catch (IOException | SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "secondRiskMngCallback")
    public void staySuppAccessoryInfoQuery() {
        try {
            json = IncomingPartsPort.staySupplementAccessoryListQuery(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test(dependsOnMethods = "staySuppAccessoryInfoQuery")
    public void supplementAccessoryInfo() {
        try {
            json = IncomingPartsPort.supplementInfoCommit(PRODUCT_ID, creditApplyId,
                    realName, regInfoQuery.getString("userId"), "main");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

}
