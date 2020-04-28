package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.PasswordAdminData;
import utils.Okhttp;
import java.io.IOException;


public class PasswordAdmin {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final String PWD_SET_URL = DOMAIN + "/v1/customerPassword/setPassword";
    private static final String EDIT_PWD_URL = DOMAIN + "/v1/customerPassword/modifiedPassword";
    private static final String RESET_PWD_URL = DOMAIN + "/v1/customerPassword/resetPassword";
    private static final String TRADE_SET_PWD_URL = DOMAIN + "/v1/customer/saveTradePassword";
    private static final String EDIT_TRADE_PWD_URL = DOMAIN + "/v1/customer/modifiedTradePassword";
    private static final String RESET_TRADE_PWD_URL = DOMAIN + "/v1/customer/resetTradePassord";
    private static final String LOGIN_INFO_QUERY_URL = DOMAIN + "/v1/customerQuery/passwordView";
    private static final String VERIFY_TRADE_PWD_URL = DOMAIN + "v1/customerQuery/passwordView";

    //设置密码
    public static JSONObject setPassword(String pwd) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(PWD_SET_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.setPassword(pwd)
                        ).toJSONString())
        );
    }

    //修改密码
    public static JSONObject editPassword(String oldPwd,String newPwd)
            throws IOException {

        return Okhttp.analysisToJson(
                Okhttp.doPost(EDIT_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.editPassword(oldPwd, newPwd)
                        ).toJSONString())
        );
    }

    //客户登陆密码重置 未完成返回错误
    public static JSONObject resetPassword(String resetType,String mobile,
                                           String certification,
                                           String newPassword)
            throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(RESET_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.resetPassword(resetType,mobile,
                                        certification,newPassword)
                        ).toJSONString())
        );
    }

    //设置交易密码
    public static JSONObject setTradePassword(String pwd) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(TRADE_SET_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.setTradePassword(pwd)
                        ).toJSONString())
        );
    }

    //修改交易密码
    public static JSONObject editTradePassword(String oldPassword,String newPassword)
            throws IOException {

        return Okhttp.analysisToJson(
                Okhttp.doPost(EDIT_TRADE_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.editPassword(oldPassword, newPassword)
                        ).toJSONString())
        );
    }

    //重置交易密码 接口地址错误  未完成
    public static JSONObject resetTradePassword(String code,String newPassword) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(RESET_TRADE_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.restTradePassword(code,newPassword)
                        ).toJSONString())
        );
    }

    //客户登录信息查询
    public static JSONObject loginInfoQuery() throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(LOGIN_INFO_QUERY_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.loginInfoQuery()
                        ).toJSONString())
        );
    }

    public static JSONObject verifyTradePassword(String tradeCode) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(VERIFY_TRADE_PWD_URL,
                        Okhttp.requestBody(
                                PasswordAdminData.loginInfoQuery()
                        ).toJSONString())
        );
    }

    public static void main(String[] args) throws IOException {
//        setPassword("set123456","6313E651B3DA4E12A95205EA94B2C467");
//        editPassword("123456","new123456");

//        PublicFunc.identityCode("001","13511111169");
//        resetPassword("0","15756240257",
//                null,"123");
//        setTradePassword("trade123456");
//        editTradePassword();
//        resetTradePassword("807107","newTrade123","2BB067779A494C6397176344E8622F6A");
        loginInfoQuery();
//        verifyTradePassword("","");
    }
}
