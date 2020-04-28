package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.AccountBindingData;
import utils.Okhttp;

import java.io.IOException;

public class AccountBinding {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final String BANK_CARD_DISCERN_URL = DOMAIN + "/v1/customerAccount/cardOCRRecognition";
    private static final String BANK_CARD_BIND_URL = DOMAIN + "/v1/customerAccount/personalBindCard";
    private static final String ACCOUNT_UNBIND_URL = DOMAIN + "/v1/customerAccount/accountUnbindCard";
    private static final String BIND_PRIMARY_ACCOUNT_URL = DOMAIN + "/v1/customerAccount/saveMasterAccount";
    private static final String BIND_ACCOUNT_INFO_QUERY_URL = DOMAIN + "/v1/customerQuery/accountBindCardsQuery";
    private static final String LIST_QUERY_RUL = DOMAIN + "/v1/customerAccount/queryValidBanks";
    private static final String BANK_CARD_INFO_QUERY_URL = DOMAIN + "/v1/customerQuery/personalCardsBINQuery";


    //银行卡orc识别
    public static JSONObject bankCardDiscern() throws IOException {

        return Okhttp.analysisToJson(Okhttp.doPost(BANK_CARD_DISCERN_URL,
                Okhttp.requestBody(AccountBindingData.bankCardIdentity()).toJSONString()
        ));
    }
    //银行卡绑定
    public static JSONObject bankCardBind(String account,String code) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(BANK_CARD_BIND_URL,
                Okhttp.requestBody(AccountBindingData.bankCardBinding(account, code)).toJSONString()
        ));
    }

    //账户解绑  账号验证异常 未完成
    public static JSONObject accountUnbind(String accountId,String code) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(ACCOUNT_UNBIND_URL,
                Okhttp.requestBody(AccountBindingData.unbind(accountId, code)).toJSONString()
        ));
    }

    //绑定主账户  账号验证异常
    public static JSONObject bindPrimaryAccount(String accountId,String code) throws IOException {

        return Okhttp.analysisToJson(Okhttp.doPost(BIND_PRIMARY_ACCOUNT_URL,
                Okhttp.requestBody(AccountBindingData.unbind(accountId, code)).toJSONString()
        ));
    }

    //绑定账户信息查询
    public static JSONObject bindAccountInfoQuery() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(BIND_ACCOUNT_INFO_QUERY_URL,
                Okhttp.requestBody(AccountBindingData.infoQuery()).toJSONString()
        ));
    }

    //支持银行列表查询  未完成  接口地址错误
    public static JSONObject listQuery() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(LIST_QUERY_RUL,
                Okhttp.requestBody(AccountBindingData.listQuery()).toJSONString()
        ));
    }

    //银行卡信息查询   返回数据与接口文档不一样
    public static JSONObject bankCardInfoQuery(String account) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(BANK_CARD_INFO_QUERY_URL,
                Okhttp.requestBody(AccountBindingData.bankCardInfoQuery(account)).toJSONString()
        ));
    }


    public static void main(String[] args) throws IOException {
        bankCardDiscern();

//        PublicFunc.identityCode("001","13511111169");
//        bankCardBind("6217995530006513540","501224","5A43D3A48F09404AA9E6335A646542AD");
//        accountUnbind("RES1","124805","5A43D3A48F09404AA9E6335A646542AD");
//        bindPrimaryAccount("RES1","124805","5A43D3A48F09404AA9E6335A646542AD");
//        bindAccountInfoQuery("5A43D3A48F09404AA9E6335A646542AD");
//        listQuery();
//        bankCardInfoQuery("6217995530006513540","5A43D3A48F09404AA9E6335A646542AD");

    }

}
