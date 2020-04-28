package business_logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import data.publicdata.RealNameAuth;
import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.CustomAuthentication;
import request.custom_admin_bus.CustomQuery;
import request.custom_admin_bus.CustomRegister;
import utils.FileUtils;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;

import static data.publicdata.PublicFunc.respAssert;

public class SpouseIncomingPartsFlow {

    private final String mainMobileNo = PublicFunc.getMobileNo();
    private JSONObject correlationInfo;
    private JSONObject json;
    private String mobileNo;
    private JSONObject spouseRegInfoQuery;
    private JSONObject realName;

    public void beforeClass(){
        String sql = "select credit_apply_info.apply_content from credit_apply_info where credit_apply_id = " +
                "(SELECT dev_credit.credit_apply.credit_apply_id FROM dev_credit.credit_apply WHERE user_id = " +
                "(SELECT user_id FROM dev_user.user_contact_station_info WHERE station_id = " +
                "(SELECT station_id FROM dev_user.user_telephone_info WHERE telephone = " +
                "\""+mainMobileNo+"\" LIMIT 1)))";

        try {
            correlationInfo = JSON.parseObject(SqlUtils.select(sql,"dev_credit"));
            mobileNo = correlationInfo.getString("spousePhone");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    public void customRegister(){
        try {

            json = CustomRegister.register(mobileNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void customRegInfoQuery(){
        try {
            json = CustomQuery.regInfoQuery(mobileNo);
            spouseRegInfoQuery = json.getJSONObject("body");

        } catch (IOException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }


    public void realNameAuth(){
        try {

            realName = RealNameAuth.realNameData(correlationInfo.getString("spouseName"),
                    correlationInfo.getString("spouseCertificateNo"),
                    spouseRegInfoQuery.getString("userId")
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
            json = IncomingPartsPort.preCreditApplySave(realName,
                    SqlUtils.select(sql,"dev_credit"),spouseRegInfoQuery.getString("userId"));
            FileUtils.writerIdentityInfo("spousePreCreditApplySave",json.getJSONObject("body"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        respAssert(json);
    }
}
