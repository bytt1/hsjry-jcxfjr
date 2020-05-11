package data.custom_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;

public class AccountBindingData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    //银行卡识别
    public static JSONObject bankCardIdentity(){

        JSONObject attachments = new JSONObject();
        attachments.put("fileKind",54); // 必传
        attachments.put("fileUrl", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2122152012,1195755948&fm=26&gp=0.jpg"); // 必传
        attachments.put("fileName","bankCard"); // 必传

        JSONArray array = new JSONArray();
        array.add(attachments);

        JSONObject bank = publicField;

        bank.put("attachments", array);    //

        return bank;
    }

    //绑定银行卡
    public static JSONObject bankCardBinding(String accountName,String certNo,
                                             String account,String mobileNo,String tradePwd) throws IOException {
        JSONObject bind = new JSONObject();
        bind.put("userId","");
        bind.put("authMode",2); // 必传  绑定模式 0仅鉴权 1仅登记 2鉴权并登记
        bind.put("account",account); //  必传 银行卡号
        bind.put("accountName",accountName); // 账户名
        bind.put("certNo",certNo); //
        bind.put("accountKind","0"); // 银行卡类别
        bind.put("mobileNo",mobileNo); // 预留手机号
        bind.put("unionBankId","102651086203"); // 条件必传（个人银行卡的开户银行支付联行行号
                                    //绑定模式为仅登记或鉴权并登记且非本行账户不能为空）
        bind.put("unionBankName","中国工商银行"); // 支付行名
        bind.put("tradePassword",tradePwd); //  条件必传（若需要校验客户交易密码则不能为空）   交易密码
        bind.put("smsCode",PublicFunc.getCode(Okhttp.getPropertiesVal("logEnv"),mobileNo)); // 条件必传（若需要校验客户短信验证码则
                                // 绑定模式为仅鉴权或鉴权并登记时不能为空）
        return bind;
    }

    //解绑或主账户设置
    public static JSONObject unbind(String accountId,String code){
        JSONObject unbind = publicField;

        unbind.put("accountId",accountId);  // 必传 绑定识别码
        unbind.put("tradePassword","");  // 条件必传
        unbind.put("smsCode",code);  // 条件必传

        return unbind;
    }

    //绑定账户查询
    public static JSONObject infoQuery(String userId){
        JSONObject info = new JSONObject();
        info.put("userId",userId);
        info.put("thirdUserId","");

        return info;
    }

    public static JSONObject listQuery(){
        return new JSONObject();
    }

    //银行卡信息查询
    public static JSONObject bankCardInfoQuery(String account){
        JSONObject info = new JSONObject();
        info.put("account",account); // 必传

        return info;
    }


}
