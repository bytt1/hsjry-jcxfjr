package business_logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import data.publicdata.RealNameAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.AccountBinding;
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
    private final String MAIN_MOBILE = PublicFunc.getMobileNo();
    private String  creditApplyId;
    private JSONObject json;
    private String mobileNo;
    public String userName;
    public String certNo;
    private JSONObject corrRegInfoQuery;
    private JSONObject realName;
    private String mainCreditApplyId;
    private JSONArray loanPeriod;
    private String userId;


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
            userId = corrRegInfoQuery.getString("userId");
            FileUtils.writerIdentityInfo("corrRegInfoQuery",corrRegInfoQuery);
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
            FileUtils.writerIdentityInfo("coBroRealName",realName);


        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    public void accountBind(){

        try {
            JSONObject data = PublicFunc.getRealNameData();
            json = AccountBinding.bankCardBind(realName.getString("userName"),realName.getString("certNo"),
                    data.getString("银行卡号"),
                    mobileNo,"trade123");
            FileUtils.writerIdentityInfo("数据",data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    public void accountQuery(){
        try {
            json = AccountBinding.bindAccountInfoQuery(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    public void faceDiscern(){
        try {
            json = CustomAuthentication.faceDiscern(realName,userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }

    public void faceDiscernResultQuery(){

        try {
            json = CustomAuthentication.resultQuery(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        respAssert(json);

    }

    public void preCreditApplySave(){
        String status;
        String creditStatus;

        String sql = "SELECT dev_credit.credit_apply.credit_apply_id FROM dev_credit.credit_apply" +
                " WHERE user_id = (SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
                " station_id = (SELECT station_id FROM dev_user.user_telephone_info" +
                " WHERE telephone = \""+MAIN_MOBILE+"\" LIMIT 1))";
        try {
            mainCreditApplyId = SqlUtils.select(sql);
            json = IncomingPartsPort.preCreditApplySave(realName,
                    mainCreditApplyId,corrRegInfoQuery.getString("userId"));

            creditApplyId = json.getJSONObject("body").getString("creditApplyId");

            creditStatus = "select credit_apply.`status` from credit_apply where" +
                    " credit_apply_id = \""+creditApplyId+"\";";
            while (true) {
                status = SqlUtils.select(creditStatus);
                assert status != null;
                if (0 == Integer.parseInt(status)){
                    break;
                }
                Thread.sleep(3000);
            }
            log.info("预授信申请单状态 >>> " + status);

            FileUtils.writerIdentityInfo("commonPreCreditApplySave",json.getJSONObject("body"));
        } catch (IOException | SQLException | ClassNotFoundException | InterruptedException e) {
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

        try {
            json = IncomingPartsPort.commitPredictCreditInfo(creditApplyId,
                    100000.00f,loanPeriod.getString(0));
        } catch (IOException e) {
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
