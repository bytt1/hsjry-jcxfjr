package data.system_admin;

import com.alibaba.fastjson.JSONObject;

public class NoteVerifyCodeData {

    //按登录授权码发送
    public static JSONObject sendByToken(String type,String mobile){
        JSONObject token = new JSONObject();

        token.put("smsType",type);
        token.put("mobileNo",mobile);

        return token;
    }

    //验证码校验
    public static JSONObject authCodeVerify(String type,String mobile,String code){
        JSONObject auth = new JSONObject();
        auth.put("smsType",type);
        auth.put("mobileNo",mobile);
        auth.put("smsCode",code);

        return auth;
    }


}
