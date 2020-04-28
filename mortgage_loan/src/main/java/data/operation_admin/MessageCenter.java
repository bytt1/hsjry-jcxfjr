package data.operation_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;

public class MessageCenter {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    //消息通知信息查询
    public static JSONObject msgNotificationQuery(){
        JSONObject query = publicField;

        query.put("messageStatus","");  //1已读 2未读
        query.put("startDate","");
        query.put("endDate","");
        query.put("pageNum","1");
        query.put("pageSize","10");

        return query;
    }


    public static JSONObject msgNotificationStatusUpdate(String opeType,String opeFlag,
                                                         String msgIdList){
        JSONObject status = publicField;

        status.put("operateType",opeType); // 必传  处理方式 1已读 2未读 3清空
        status.put("operateFlag",opeFlag); //必传 处理方式 0按消息编号 1全部
        status.put("messageIdList",msgIdList); // 处理方式为全部时可为空

        return status;
    }

}
