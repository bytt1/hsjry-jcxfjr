package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.CustomQueryData;
import utils.Okhttp;

import java.io.IOException;

public class CustomQuery {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final String CUSTOM_INFO_QUERY = DOMAIN + "/v1/customerQuery/baseInfo";
    private static final String REGISTER_INFO_QUERY = DOMAIN + "/v1/customerQuery/queryRegInfo";

    //客户信息查询
    public static JSONObject customInfoQuery() throws IOException {

        return Okhttp.analysisToJson(Okhttp.doPost(CUSTOM_INFO_QUERY,
                Okhttp.requestBody(CustomQueryData.infoQuery()).toJSONString()));
    }

    //客户注册信息查询
    public static JSONObject regInfoQuery(String identifier) throws IOException {

        return Okhttp.analysisToJson(Okhttp.doPost(REGISTER_INFO_QUERY,
                Okhttp.requestBody(CustomQueryData.registerQuery(identifier)).toJSONString()));
    }

    public static void main(String[] args) throws IOException {
//        customInfoQuery("5A43D3A48F09404AA9E6335A646542AD");
        regInfoQuery("userId");
    }

}
