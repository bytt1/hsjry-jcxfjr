package data.custom_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;

import java.io.IOException;

public class PasswordAdminData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    //客户密码设置
    public static JSONObject setPassword(String pwd){
        JSONObject passwd = new JSONObject();
        passwd.put("password",pwd); // 必传

        return passwd;
    }

    //修改登录密码 OR 修改交易密码
    public static JSONObject editPassword(String oldPwd,String newPwd){
        JSONObject edit = new JSONObject();
        edit.put("oldPassword",oldPwd); //
        edit.put("newPassword",newPwd); //

        return edit;
    }

    //重置密码
    public static JSONObject resetPassword(String resetType,String mobile,
                                           String certification,String newPassword) throws IOException {
        String smsCode = null;
        if ("0".equals(resetType)){
            smsCode = PublicFunc.getCode("dev_user_app_log*",mobile);
        }
        JSONObject reset = new JSONObject();
        reset.put("resetType",resetType); // 重置方式 必传  0按短信验证码 1按证件号码  2按证件尾号（后4位）
        reset.put("mobileNo",mobile); // 条件必传（重置方式为按短信验证码时不能为空）
        reset.put("smsCode",smsCode); // 条件必传（重置方式为按短信验证码时不能为空）
        reset.put("certNo",certification); // 条件必传（重置方式为按证件号码时不能为空）
        reset.put("newPassword",newPassword); //  必传  新的登录密码
//        reset.put("oldPassword",oldPassword);

        return reset;
    }

    //交易密码设置
    public static JSONObject setTradePassword(String pwd){
        JSONObject transaction = new JSONObject();
        transaction.put("tradePassword",pwd);

        return transaction;

    }

    //重置交易密码
    public static JSONObject restTradePassword(String code,String newTradePwd){
        JSONObject json = new JSONObject();
        json.put("smsCode",code);
        json.put("newPassword",newTradePwd);
        return json;
    }

    //客户登录信息查询
    public static JSONObject loginInfoQuery(){
        return new JSONObject();
    }

    //校验交易密码
    public static JSONObject verifyTradePassword(String tradePwd){
        JSONObject verify = publicField;

        verify.put("tradePassword",tradePwd); //  必传
        verify.put("certificateKind","0");
        verify.put("certificateNo","");

        return verify;
    }



}
