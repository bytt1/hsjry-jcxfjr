package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.CustomRegisterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Okhttp;

import java.io.IOException;

public class CustomRegister {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRegister.class);
    private static final String DOMAIN = Okhttp.getDomain();
    private static final String REG_URL = DOMAIN + "/v1/customerRegister/registerByTelephone";
    private static final String EDIT_URL = DOMAIN + "/v1/customer/modifyCustInfo";
    private static final String EDITPHONE_URL = DOMAIN + "/v1/customer/projectModifyMobile";
    private static final String IMG_URL = DOMAIN + "/v1/customer/saveHeadUrl";
    private static final String RESERVED_URL = DOMAIN + "/v1/customer/saveReservedInfo";
    private static final String INFO_LOGOUT_URL = DOMAIN + "/v1/login/delCustInfo";
    private static final String VERIFY_IDENTITY_URL = DOMAIN + "/v1/customer/checkCert";
    private static final String PHONE = Okhttp.getPropertiesVal("phone");



    public static JSONObject register(String mobileNo) throws IOException {
        JSONObject json = Okhttp.analysisToJson(Okhttp.doPost(REG_URL,
                Okhttp.requestBody(CustomRegisterData.regLogin(mobileNo)).toJSONString()));
        Okhttp.setPro("token",json.getJSONObject("body").getString("token"));
        return json;
    }


    //修改客户信息
    public static JSONObject editInfo() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(EDIT_URL,
                Okhttp.requestBody(CustomRegisterData.editInfo()).toJSONString()));
    }

    //修改手机号  token传公参  未完成
    public static JSONObject editPhone(String phone,String code) throws IOException {
        JSONObject json = Okhttp.analysisToJson(Okhttp.doPost(EDITPHONE_URL,
                Okhttp.requestBody(CustomRegisterData.editPhone(phone,code)).toJSONString()));

        return json;
    }

    //设置头像
    public static JSONObject imgSet() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(IMG_URL,
                Okhttp.requestBody(CustomRegisterData.setHeadImg()).toJSONString()));
    }

    //客户预留信息设置
    public static JSONObject reservedInfoSet() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(RESERVED_URL,
                Okhttp.requestBody(CustomRegisterData.reservedInfo("客户预留信息设置 >> "
                        + Okhttp.getFont(20))).toJSONString()));
    }

    //客户信息注销  未完成  返回系统异常
    public static JSONObject customInfoLogout() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(INFO_LOGOUT_URL,
                Okhttp.requestBody(CustomRegisterData.infoLogout()).toJSONString()));
    }

    //校验身份信息
    public static JSONObject verifyIdentityInfo(JSONObject identityInfoResp) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(VERIFY_IDENTITY_URL,
                Okhttp.requestBody(CustomRegisterData.verifyInfo(identityInfoResp)).toJSONString()));
    }



    public static void main(String[] args) throws IOException {
        register("15756240258");
//        editInfo();
//        editPhone();
//        imgSet();
//        reservedInfoSet();
//        customInfoLogout();

//        verifyIdentityInfo(JSON.parseObject(getProVal("identityDiscern")));

    }

}
