package incomingParts_flow_test;

import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.CustomAuthentication;
import request.custom_admin_bus.CustomQuery;
import request.custom_admin_bus.CustomRegister;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import data.third_party_interface_admin.RiskManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.Okhttp;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;

import static data.publicdata.PublicFunc.firRiskMngCallback;
import static utils.Okhttp.getProVal;

public class IncomingPartsBusinessFlowTest {

    private final Logger log = LoggerFactory.getLogger(IncomingPartsBusinessFlowTest.class);
    private final String PRODUCT_ID = Okhttp.getPropertiesVal("productId");
    private JSONObject json;
    private final String mobileNo = Okhttp.getPropertiesVal("mobileNo");
    private JSONObject identityInfo;
    private JSONObject preCrtAlySaveResp;
    private JSONObject regInfoQuery;
    private String creditApplyId;
    private final String SPOUSE_PHONE = Okhttp.getPhone();


    @Test
    public void customRegister(){
        try {

            json = CustomRegister.register(mobileNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void customRegInfoQuery(){
        try {
            json = CustomQuery.regInfoQuery(mobileNo);
            regInfoQuery = json.getJSONObject("body");

        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }


    @Test
    public void identityDiscern(){
        try {
            json = CustomAuthentication.identityDiscern(true,false);
            identityInfo = JSON.parseObject(getProVal("identityDiscern"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void verifyIdentityInfo(){
        try {
            json = CustomRegister.verifyIdentityInfo(identityInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }


    @Test
    public void realNameAuth(){
        try {
            json = CustomAuthentication.realNameAuth(identityInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void faceDiscern(){
        try {
            json = CustomAuthentication.faceDiscern(identityInfo,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void faceDiscernResultQuery(){
        try {
            json = CustomAuthentication.resultQuery("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void preCreditApplySave(){
        try {
            json = IncomingPartsPort.preCreditApplySave(identityInfo,"",
                    regInfoQuery.getString("userId"));
            preCrtAlySaveResp = JSON.parseObject(getProVal("preCreditApplySave"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }


    @Test
    public void firstRiskCallback(){
        creditApplyId = preCrtAlySaveResp.getString("creditApplyId");
        String serial;
        String sql = "select sequence_id from dev_credit.credit_risk_flow where biz_apply_id=\""+creditApplyId+"\";";
        try {
            serial  = SqlUtils.select(sql);
            json = firRiskMngCallback(serial);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void houseInfoMng(){
        try {
            json = IncomingPartsPort.houseInfoManagement(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void regMarriageInfo(){
//        try {
//            json = IncomingPartsPort.registerMarriageInfo(creditApplyId,SPOUSE_PHONE,regInfoQuery.getString("userId"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        PublicFunc.respAssert(json);
    }

    @Test
    public void addCorrelationPerson(){
//        try {
//            json = IncomingPartsPort.addCorrelationPerson(preCrtAlySaveResp.getString("mainCreditApplyId"),
//                    regInfoQuery.getString("userId"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        PublicFunc.respAssert(json);

    }

    @Test
    public void correlationPerInfoQuery(){
        try {
            json = IncomingPartsPort.correlationInfoQuery(preCrtAlySaveResp.getString("mainCreditApplyId"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void loanChoiceDeadlineQuery(){
        try {
            json = IncomingPartsPort.loanChoiceDeadlineQuery(preCrtAlySaveResp.getString("mainCreditApplyId"),
                    preCrtAlySaveResp.getString("creditApplyId"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void commitPredictCreditInfo(){
        try {
            json = IncomingPartsPort.commitPredictCreditInfo(preCrtAlySaveResp.getString("creditApplyId"),
                    100000.00f,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void firstMortgageInfoListQuery(){
        try {
            json = IncomingPartsPort.firstMortgageInfoListQuery(PRODUCT_ID,
                    preCrtAlySaveResp.getString("mainCreditApplyId"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }


    @Test
    public void afterCorrelationAuthCommitCredit(){
        try {
            json = IncomingPartsPort.afterAllCorrelationAuthCommitCredit(PRODUCT_ID,
                    preCrtAlySaveResp.getString("mainCreditApplyId"),identityInfo.getString("userName"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicFunc.respAssert(json);
    }

    @Test
    public void assetLiabilityQuery(){
        json = RiskManagement.assetLiabilityRatioQuery(PRODUCT_ID,
                preCrtAlySaveResp.getString("mainCreditApplyId"),
                preCrtAlySaveResp.getString("creditApplyId"));
        PublicFunc.respAssert(json);
    }

    @Test
    public void incomeVerify(){
        json = RiskManagement.incomeVerify(preCrtAlySaveResp.getString("creditApplyId"));
        PublicFunc.respAssert(json);
    }

}
