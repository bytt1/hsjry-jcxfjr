package data.operation_admin;

import com.alibaba.fastjson.JSONObject;

public class AdvertisingData {

    public static JSONObject osAdvertisingInfoQuery(JSONObject advertising){
        JSONObject info = new JSONObject();
        info.put("adId",advertising.get("adId"));
        info.put("adName",advertising.get("adName"));
        info.put("adState",advertising.get("adState"));
        info.put("channelNoList",advertising.get("channelNoList"));
        info.put("validityStartTime",advertising.get("validityStartTime"));
        info.put("validityEndTime","2021/12/30 00:00:00");
        info.put("pageNum","1");
        info.put("pageSize","10");

        return info;
    }
}
