package data.operation_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;

public class SupportCenter {

    //帮助目录信息查询
    public static JSONObject supportCatalogInfoQuery(){
        JSONObject support = new JSONObject();
        support.put("catalogCode","");  //
        support.put("catalogName","");  //
        support.put("parentCatalogCode","");  //
        support.put("queryType","");  // 必传 0 - 不包含下级
                                        //        1 - 包含直接下级
                                        //        2 - 包含所有下级

        support.put("pageNum","1");  // 必传
        support.put("pageSize","10");  // 必传

        return support;
    }


    //帮助问题信息查询
    public static JSONObject supportQuestionQuery(){
        JSONObject question = new JSONObject();
        question.put("questionCode","");
        question.put("questionTitle","");
        question.put("catalogCode",""); //目录代码非空时有效
        question.put("queryType","");
        question.put("pageNum","1");  // 必传
        question.put("pageSize","10");  // 必传

        return question;
    }

    public static JSONObject problemFeedback(){
        JSONObject back = new JSONObject();

        back.put("feedbackType","");
        back.put("feedbackContent","");
        back.put("contactInfo","");
        back.put("attachments", PublicFunc.attachments());

        return back;
    }

}
