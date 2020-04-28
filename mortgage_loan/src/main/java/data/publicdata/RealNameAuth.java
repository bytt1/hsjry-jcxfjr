package data.publicdata;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;


public class RealNameAuth {

    public static JSONObject getIdentityInfo() throws IOException {
        JSONObject resp = Okhttp.analysisToJson(Okhttp.doGet("http://10.10.61.77:8081/getTestData"));
        JSONObject json = new JSONObject();
        json.put("userName",resp.getString("姓名"));
        json.put("email",resp.getString("邮箱"));
        json.put("mobileNo",resp.getString("手机号"));
        json.put("address",resp.getString("地址"));
        json.put("certNo",resp.getString("身份证号"));
        json.put("bankCardId",resp.getString("银行卡号"));

        return json;
    }

    public static JSONObject realNameData(String userName,String certNo,String userId){


        JSONObject autonym = new JSONObject();
        autonym.put("userId",userId);
        autonym.put("authMode", 0);  // 必传 0仅认证 1仅登记 2认证并登记
        autonym.put("userName", userName);  // 必传
        autonym.put("certType", "0");  // 必传
        autonym.put("certNo", certNo);  // 必传
        autonym.put("dueDate", "20260915");
        autonym.put("gender", 1);
        autonym.put("nation", "汉");  // 民族
        autonym.put("signOrgan", "成都市公安局");  //  签发机关
        autonym.put("issueDate", "20060915");  //  签发日期
        autonym.put("pmtAddrProvinceCode", "230000"); // 户籍省份代码
        autonym.put("pmtAddrCityCode", "231000"); //  户籍城市代码
        autonym.put("pmtAddrAreaCode", "231025"); //  户籍地区代码
        autonym.put("pmtAddress", Okhttp.getFont(6)); // 户籍详细地址
        autonym.put("attachments", PublicFunc.attachments());
        autonym.put("certificateCheck","1");
        autonym.put("certificateAddress",null);

        return autonym;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(realNameData());
    }
}
