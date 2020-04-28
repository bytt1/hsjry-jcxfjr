package data.custom_admin;

import com.alibaba.fastjson.JSONObject;


public class UserLoginData {

    //账号密码登录
    public static JSONObject accountLogin(String account,String pwd) {
        JSONObject login = new JSONObject();
        login.put("identifier",account);//支持手机号码、登录名、邮箱、证件号码、银行卡号等其中之一
        login.put("password",pwd);//

        return login;
    }

    //验证码登录
    public static JSONObject verifyCodeLogin(String phone,String verifyCode){
        JSONObject code = new JSONObject();
        code.put("mobileNo",phone);// 必传
        code.put("smsCode",verifyCode);//  必传
        code.put("autoRegFlag","");// 为空默认为否  自动注册标识

        return code;
    }

    //退出登录
    public static JSONObject quitLogin(){
        return new JSONObject();
    }


}
