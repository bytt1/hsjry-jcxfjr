package data.operation_admin;

import com.alibaba.fastjson.JSONObject;

public class NoticeData {

    public static JSONObject osNoticeInfoQuery(JSONObject notice){
        JSONObject info = new JSONObject();
        info.put("bulletinId",notice.get(""));
        info.put("bulletinTitle",notice.get(""));
        info.put("bulletinType",notice.get(""));
        info.put("topFlag",notice.get(""));
        info.put("channelNoList",notice.get(""));
        info.put("startDate",notice.get(""));
        info.put("endDate","2021/12/30 00:00:00");
        info.put("pageNum","1");
        info.put("pageSize","10");

        return info;
    }
}
