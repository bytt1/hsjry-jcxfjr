package business_logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import data.publicdata.RealNameAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.CustomAuthentication;
import request.custom_admin_bus.CustomQuery;
import request.custom_admin_bus.CustomRegister;
import utils.FileUtils;
import utils.Okhttp;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;

import static data.publicdata.PublicFunc.respAssert;

public class CorrelationIncomingPartsFlow {

    private final Logger log = LoggerFactory.getLogger(CorrelationIncomingPartsFlow.class);
    private final String mainMobileNo = PublicFunc.getMobileNo();
    private String  creditApplyId;
    private JSONObject json;
    private String mobileNo;
    public String userName;
    public String certNo;
    private JSONObject corrRegInfoQuery;
    private JSONObject realName;
    private String mainCreditApplyId;
    private JSONArray loanPeriod;


    public void customRegister(){
        try {
            mobileNo = Okhttp.getPhone();
            json = CustomRegister.register(mobileNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void customRegInfoQuery(){
        try {
            json = CustomQuery.regInfoQuery(mobileNo);
            corrRegInfoQuery = json.getJSONObject("body");

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void realNameAuth(){
        try {
            log.info("用户姓名 >>> " + userName + "   " + "身份证号 >>> " + certNo);
            realName = RealNameAuth.realNameData(userName,
                    certNo,
                    corrRegInfoQuery.getString("userId")
            );

            json = CustomAuthentication.realNameAuth(realName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void preCreditApplySave(){
        String sql = "SELECT dev_credit.credit_apply.credit_apply_id FROM dev_credit.credit_apply" +
                " WHERE user_id = (SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
                " station_id = (SELECT station_id FROM dev_user.user_telephone_info" +
                " WHERE telephone = \""+mainMobileNo+"\" LIMIT 1))";
        try {
            mainCreditApplyId = SqlUtils.select(sql,"dev_credit");
            json = IncomingPartsPort.preCreditApplySave(realName,
                    mainCreditApplyId,corrRegInfoQuery.getString("userId"));
            creditApplyId = json.getJSONObject("body").getString("creditApplyId");
            FileUtils.writerIdentityInfo("spousePreCreditApplySave",json.getJSONObject("body"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void loanChoiceDeadlineQuery(){
        try {
            json = IncomingPartsPort.loanChoiceDeadlineQuery(mainCreditApplyId,
                    creditApplyId);
            loanPeriod = json.getJSONObject("body").getJSONArray("loanTermList");
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void commitPredictCreditInfo(){
        String sql = "select credit_apply.`status` from credit_apply where" +
                " credit_apply_id = \""+creditApplyId+"\";";
        String status;
        try {
            while (true) {
                status = SqlUtils.select(sql, "dev_credit");
                assert status != null;
                if (0 == Integer.parseInt(status)){
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("预授信申请单状态 >>> " + status);
            json = IncomingPartsPort.commitPredictCreditInfo(creditApplyId,
                    100000.00f,loanPeriod.getString(0));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    public void staySupplementInfoQuery(){
        try {
            json = IncomingPartsPort.staySupplementAccessoryListQuery(creditApplyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supplementInfoCommit(){
        try {
            json = IncomingPartsPort.supplementInfoCommit(PublicFunc.getProductID(),
                    creditApplyId,realName,corrRegInfoQuery.getString("userId"),"common");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
