package request.custom_admin_bus;

import com.alibaba.fastjson.JSONObject;
import data.custom_admin.CustomAuthData;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;
import java.sql.SQLException;

import static utils.Okhttp.writerFlag;

public class CustomAuthentication {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final String IDENTITY_DISCERN_URL = DOMAIN + "/v1/customer/ocrRecognition";
    private static final String REAL_NAME_URL = DOMAIN + "/v1/customer/projectCheckedRealName";
    private static final String FACE_DISCERN_URL = DOMAIN + "/v1/risk/faceRecognition";
    private static final String RESULT_QUERY_URL = DOMAIN + "/v1/risk/faceRecognitionResult";
    private static final String AUTH_STATUS_QUERY_URL = DOMAIN +"/v1/credit/userCheckView";

    //ORC身份识别
    public static JSONObject identityDiscern(boolean clearFlag,boolean coFlag) throws IOException {
        JSONObject json = Okhttp.analysisToJson(
                Okhttp.doPost(IDENTITY_DISCERN_URL,
                        Okhttp.requestBody(CustomAuthData.identityDiscern()).toJSONString()));
        if (!coFlag) {
            writerFlag(json, "identityDiscern", clearFlag);
        }else {
            writerFlag(json, "commonLoanInfo", clearFlag);
        }


        return json;
    }

    //实名身份认证
    public static JSONObject realNameAuth(JSONObject identityDiscernResponse) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(REAL_NAME_URL,
                        Okhttp.requestBody(CustomAuthData
                                .realNameAuth(identityDiscernResponse)).toJSONString()));
    }

    //人脸识别
    public static JSONObject faceDiscern(JSONObject identityDiscernResp,String userId) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(FACE_DISCERN_URL,
                        Okhttp.requestBody(CustomAuthData.faceIdentity(identityDiscernResp,userId)).toJSONString())
        );
    }

    public static JSONObject corrFaceDiscern(JSONObject identityDiscernResp) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(FACE_DISCERN_URL,
                        Okhttp.requestBody(CustomAuthData.correlationFaceIdentity(identityDiscernResp)).toJSONString())
        );
    }


    //人脸识别结果查询
    public static JSONObject resultQuery(String userId) throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(RESULT_QUERY_URL,
                        Okhttp.requestBody(CustomAuthData.faceDiscernQuery(userId)).toJSONString())
        );
    }

    //认证状态查询
    public static JSONObject authStatusQuery() throws IOException {
        return Okhttp.analysisToJson(
                Okhttp.doPost(AUTH_STATUS_QUERY_URL,
                        Okhttp.requestBody(CustomAuthData.authStatusQuery()).toJSONString())
        );
    }


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        identityDiscern(true,false);
//        realNameAuth(RealNameAuth.realNameData());
//        faceDiscern(JSON.parseObject(getProVal("identityDiscern")));
        UserLogin.loginByAccount("13715435472","123456");
        resultQuery(PublicFunc.getUserId("13715435472"));

//        authStatusQuery("","","5A43D3A48F09404AA9E6335A646542AD");
    }

}
