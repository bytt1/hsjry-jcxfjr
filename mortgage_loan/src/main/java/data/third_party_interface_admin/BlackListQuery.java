package data.third_party_interface_admin;

import com.alibaba.fastjson.JSONObject;

public class BlackListQuery {

    //客户黑名单查询
    public static JSONObject blackListQuery(String userTel,String certificateNo){
        JSONObject query = new JSONObject();
        query.put("userTel",userTel);
        query.put("certificateNo",certificateNo);

        return query;
    }

}
