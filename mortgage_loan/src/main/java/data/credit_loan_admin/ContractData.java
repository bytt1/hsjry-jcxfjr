package data.credit_loan_admin;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;

public class ContractData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    //合同模板详情查询
    public static JSONObject templateDetailQuery(String compactId){
        JSONObject temp = new JSONObject();
        temp.put("compactId",compactId); //

        return temp;
    }

    //合同列表查询
    public static JSONObject templateListQuery(){
        JSONObject list = publicField;
        list.put("userName",""); //
        list.put("certificateNo",""); //
        list.put("mobileNo",""); //
        list.put("contractId",""); //
        list.put("contractName",""); //
        list.put("contractType","1"); //
        list.put("contractStatus","1"); //
        list.put("creditLimitId",""); //  授信额度唯一识别码
        list.put("loanInvoiceId",""); //  贷款借据唯一识别码
        list.put("status","1"); // 借据状态   1 - 使用中
                                            //        2 - 已逾期
                                            //        3 - 已结清
                                            //        X - 未结清
        list.put("pageNum","1"); //
        list.put("pageSize","10"); //

        return list;
    }


    //合同模板信息查询
    public static JSONObject templateInfoQuery(String compactType,String productId){
        JSONObject info = new JSONObject();
        info.put("compactType",compactType); // 必传
        info.put("productId",productId); //

        return info;
    }

}
