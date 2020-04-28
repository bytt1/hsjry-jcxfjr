package data.operation_admin;

import com.alibaba.fastjson.JSONObject;

public class MarketingData {

    //活动信息查询
    public static JSONObject activityInfoQuery(JSONObject activity){
        JSONObject info = new JSONObject();
        info.put("activityId",activity.get("activityId")); // 唯一识别码
        info.put("activityName",activity.get("activityName"));
        info.put("activityType",activity.get("activityType"));
        info.put("channelNoList",activity.get("channelNoList"));
        info.put("partakeType",activity.get("partakeType")); //  参与方式
        info.put("validityStartTime",""); // 有效起始时间
        info.put("validityEndTime","2021/12/30 00:00:00"); //  截止时间
        info.put("activityState",activity.get("activityState"));
        info.put("pageNum","1");
        info.put("pageSize","10");

        return info;
    }
}
