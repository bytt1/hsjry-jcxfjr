package data.system_admin;

import com.alibaba.fastjson.JSONObject;

public class BasicDataAdmin {

    //数据字典信息查询
    public static JSONObject dataDictionaryQuery(String[] dictKeyList){
        JSONObject data = new JSONObject();
        data.put("dictKeyList",dictKeyList); //必传 字典类别代码列表

        return data;
    }

    //省市区域信息查询
    public static JSONObject areaInfoQuery(){
        JSONObject area = new JSONObject();
        area.put("queryType",""); //1身份 2城市 3区域
        area.put("provinceCode","");
        area.put("cityCode","");
        area.put("areaCode","");
        area.put("pageNum",""); //默认1
        area.put("pageSize",""); //默认20

        return area;
    }
}
