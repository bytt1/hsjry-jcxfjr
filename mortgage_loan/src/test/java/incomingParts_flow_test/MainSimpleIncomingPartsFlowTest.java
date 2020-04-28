package incomingParts_flow_test;

import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.CustomAuthentication;
import request.custom_admin_bus.CustomQuery;
import request.custom_admin_bus.CustomRegister;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.FileUtils;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static data.publicdata.RealNameAuth.realNameData;
import static data.publicdata.PublicFunc.*;
import static utils.Okhttp.*;

public class MainSimpleIncomingPartsFlowTest {

    private final Logger log = LoggerFactory.getLogger(IncomingPartsBusinessFlowTest.class);
    private final String PRODUCT_ID = getProductID();
    private JSONObject json;
    private final String SPOUSE_PHONE = getSpouseMobileNo();
    private final String MAIN_MOBILE = getMobileNo();
    private String userID;
    private JSONObject preCreditApplySave;
    private JSONObject regInfoQuery;
    private JSONObject realName;
    private String creditApplyId;
    private String sql = "select sequence_id,create_time from dev_credit.credit_risk_flow where biz_apply_id=";
    private JSONArray loanPeriod;


    @Test
    public void customRegister(){
        try {

            json = CustomRegister.register(MAIN_MOBILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void customRegInfoQuery(){
        try {
            json = CustomQuery.regInfoQuery(MAIN_MOBILE);
            userID = json.getJSONObject("body").getString("userId");
            FileUtils.writerIdentityInfo("regInfoQuery",json.getJSONObject("body"));
            regInfoQuery = json.getJSONObject("body");

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void realNameAuth(){
        try {
            String certNo = withinSixHundred() + withinSixHundred() + "19981008" + fourRandom();
            realName = realNameData(getFont(3),certNo
                    ,getProVal("userId"));
            json = CustomAuthentication.realNameAuth(realName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void preCreditApplySave(){
        try {
            json = IncomingPartsPort.preCreditApplySave(realName,"",userID);
            preCreditApplySave = json.getJSONObject("body");
            log.info("预授信保存响应报文 >>> " + preCreditApplySave);
            creditApplyId = preCreditApplySave.getString("creditApplyId");
            log.info("creditApplyId >>> " + creditApplyId);
            FileUtils.writerIdentityInfo("preCreditApplySave",preCreditApplySave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void firstRiskCallback(){

        try {
            String seqId = SqlUtils.select(
                    sql + "\""+creditApplyId+"\" order by create_time desc limit 1;",
                    "dev_credit");

            log.info("一次风控回调sequence_id >>> " + seqId);
            json = firRiskMngCallback(seqId);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        respAssert(json);

    }

    @Test
    public void houseInfoMng(){
        try {
            json = IncomingPartsPort.houseInfoManagement(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void regMarriageInfo(){
        try {
            log.info("配偶电话 >>> " + SPOUSE_PHONE);
            json = IncomingPartsPort.registerMarriageInfo(creditApplyId,SPOUSE_PHONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void addCorrelationPer(){
        try {
            json = IncomingPartsPort.addCorrelationPerson(creditApplyId,userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test
    public void loanChoiceDeadlineQuery(){
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
    public void commitPredictCreditInfo(){
        try {
            json = IncomingPartsPort.commitPredictCreditInfo(creditApplyId,
                    100000.00f,loanPeriod.getString(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test(dependsOnGroups = {"registerMarriageInfo","correlation"})
    public void afterAllCorrelationAuthCommitCredit() throws IOException {
        json = IncomingPartsPort.afterAllCorrelationAuthCommitCredit(PRODUCT_ID,
                preCreditApplySave.getString("mainCreditApplyId"),
                realName.getString("userName"));
        respAssert(json);
    }

    @Test(dependsOnMethods = "afterAllCorrelationAuthCommitCredit")
    public void secondRiskMngCallback(){

        String seqId;
        String sql = "SELECT f.sequence_id,f.create_time FROM dev_credit.credit_risk_flow f where" +
               " f.biz_apply_id in (SELECT a.affiliated_apply_id FROM dev_credit.credit_affiliated_info" +
               " a WHERE main_borrower_id = (SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
               " station_id = (SELECT station_id FROM dev_user.user_telephone_info WHERE" +
               " telephone = \""+MAIN_MOBILE+"\" ORDER BY create_time DESC LIMIT 1))ORDER BY" +
               " a.affiliated_apply_id ASC)order by f.create_time desc";

        log.info("主借人申请单号 >>> " + creditApplyId);

        try {

            ResultSet res = SqlUtils.selectAll(sql,"dev_credit");

            while(res.next()){
                seqId = res.getString(1);
                log.info("二次风控回调sequence_id >>> " + seqId);
                json = secRiskMngCallback(seqId);
                if (json.getJSONObject("head").getString("returnMessage").contains("幂等")){
                    continue;
                }else if(json.getJSONObject("body").getString("msg").contains("处理异常")){
                    log.info("异常风控流水 >>> " + seqId);
                }
                respAssert(json);
            }



        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "secondRiskMngCallback")
    public void staySuppAccessoryInfoQuery(){
        try {
            json = IncomingPartsPort.staySupplementAccessoryListQuery(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    @Test(dependsOnMethods = "staySuppAccessoryInfoQuery")
    public void supplementAccessoryInfo(){
        try {
            json = IncomingPartsPort.supplementInfoCommit(PRODUCT_ID,creditApplyId,
                    realName,regInfoQuery.getString("userId"),"main");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }
}
