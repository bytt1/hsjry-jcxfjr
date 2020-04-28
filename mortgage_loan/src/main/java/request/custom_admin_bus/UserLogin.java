package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.UserLoginData;
import data.publicdata.PublicFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Okhttp;
import java.io.*;
import java.util.Properties;


public class UserLogin {

    private static Properties pro;
    private static final String DOMAIN = Okhttp.getDomain();
    private static final String LOGIN_BY_ACCOUNT_URL = DOMAIN + "/v1/customerLogin/loginByPassword";
    private static final String LOGOUT_URL = DOMAIN + "/v1/customerLogin/loginOut";
    private static final String LOGIN_BY_AUTH_CODE_URL = DOMAIN + "/v1/customerLogin/loginByMessage";
    private static final String PHONE = Okhttp.getPropertiesVal("phone");
    private static final Logger log = LoggerFactory.getLogger(UserLogin.class);

    static {
        pro = new Properties();
    }




    //账号密码登录
    public static JSONObject loginByAccount(String phone,String code) throws IOException {
        JSONObject json = Okhttp.analysisToJson(Okhttp.doPost(LOGIN_BY_ACCOUNT_URL,
                Okhttp.requestBody(UserLoginData.accountLogin(phone,code)).toJSONString()));
        Okhttp.setPro("token",json.getJSONObject("body").getString("token"));

        return json;
    }

    //验证码登录
    public static JSONObject loginByVerifyCode(String mobileNo) throws IOException {
        JSONObject json = Okhttp.analysisToJson(Okhttp.doPost(LOGIN_BY_AUTH_CODE_URL,
                Okhttp.requestBody(UserLoginData.verifyCodeLogin(mobileNo,
                        PublicFunc.getCode(Okhttp.getPropertiesVal("logEnv"),mobileNo))).toJSONString()));
        Okhttp.setPro("token",json.getJSONObject("body").getString("token"));
        return json;
    }

    //退出登录
    public static void logout() throws IOException {
        Okhttp.analysisToJson(Okhttp.doPost(LOGOUT_URL,
                Okhttp.requestBody(UserLoginData.quitLogin()).toJSONString()));
    }

    public static void main(String[] args) throws IOException {
        loginByAccount("15756240257","123");

//        loginByVerifyCode("15756240257");

//        System.out.println(PublicFunc.getCode("dev_user_app_log*","15756240257"));
//        logout();
    }
}
