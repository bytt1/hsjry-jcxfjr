package data.custom_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;

public class CustomQueryData {

    //客户信息查询
    public static JSONObject infoQuery(){

        return PublicFunc.interfacePublicField("");
    }

    //注册信息查询
    public static JSONObject registerQuery(String identifier){
        JSONObject reg = new JSONObject();
        reg.put("identifier",identifier);  // 必传 客户登录账户

        return reg;
    }
}
