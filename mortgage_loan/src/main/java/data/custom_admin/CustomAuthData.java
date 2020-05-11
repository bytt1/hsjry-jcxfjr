package data.custom_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;

public class CustomAuthData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");


    //身份识别
    public static JSONObject identityDiscern(){

        JSONObject discern = publicField;

        discern.put("attachments", PublicFunc.attachments()); //

        return discern;

    }

    //实名认证
    public static JSONObject realNameAuth(JSONObject identityDiscern){

        String gender = identityDiscern.getString("gender");

        JSONObject autonym = publicField;

        autonym.put("authMode", 0);  // 必传 0仅认证 1仅登记 2认证并登记
        autonym.put("userName", identityDiscern.getString("userName"));  // 必传
        autonym.put("certType", identityDiscern.getString("certType"));  // 必传
        autonym.put("certNo", identityDiscern.getString("certNo"));  // 必传
        autonym.put("dueDate", identityDiscern.getString("dueDate"));
        if ("男".equals(gender)) {
            autonym.put("gender", 1);
        } else {
            autonym.put("gender", 2);
        }
        autonym.put("nation", identityDiscern.getString("nation"));  // 民族
        autonym.put("signOrgan", identityDiscern.getString("signOrgan"));  //  签发机关
        autonym.put("issueDate", identityDiscern.getString("issueDate"));  //  签发日期
        autonym.put("pmtAddrProvinceCode", "230000"); // 户籍省份代码
        autonym.put("pmtAddrCityCode", "231000"); //  户籍城市代码
        autonym.put("pmtAddrAreaCode", "231025"); //  户籍地区代码
        autonym.put("pmtAddress", identityDiscern.getString("pmtAddress")); // 户籍详细地址
        autonym.put("attachments", PublicFunc.attachments());
        autonym.put("certificateCheck","1");
        autonym.put("certificateAddress",null);

        return autonym;
    }

    //人脸识别
    public static JSONObject faceIdentity(JSONObject identityDiscernResp,String userId){

        JSONObject face = new JSONObject();
        face.put("userId",userId);
        face.put("name", identityDiscernResp.getString("userName"));   // 必传
        face.put("certNo", identityDiscernResp.getString("certNo"));   // 必传
        face.put("callBackUrl", "http://www.baidu.com");   //    必传
        try {
            face.put("imageRef", Okhttp.bs64("C:\\Users\\Administrator\\Pictures\\certificate\\faceDiscern.jpg"));   //   人脸识别base64串
        } catch (IOException e) {
            e.printStackTrace();
        }

        return face;
    }

    public static JSONObject correlationFaceIdentity(JSONObject corrIdentityDiscernResp){

        JSONObject face = publicField;

        face.put("name",corrIdentityDiscernResp.getString("spouseName"));   // 必传
        face.put("certNo",corrIdentityDiscernResp.getString("spouseCertificateNo"));   // 必传
        face.put("callBackUrl", "http://www.sougou.com");   //    必传
        try {
            face.put("imageRef", Okhttp.bs64("C:\\Users\\Administrator\\Pictures\\certificate\\faceDiscern.jpg"));   //   人脸识别base64串
        } catch (IOException e) {
            e.printStackTrace();
        }

        return face;
    }

    //人脸识别 认证结果查询
    public static JSONObject faceDiscernQuery(String userId){
        JSONObject result = new JSONObject();
        result.put("userId",userId);
        return result;
    }

    //认证状态查询
    public static JSONObject authStatusQuery(){
        return publicField;
    }
}
