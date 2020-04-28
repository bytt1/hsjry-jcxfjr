package data.third_party_interface_admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import static utils.Okhttp.getProVal;

public class RiskManagement {

    private static final JSONObject publicField = PublicFunc.interfacePublicField("");

    //收入校验
    public static JSONObject incomeVerify(String creditApplyId){
        JSONObject income = publicField;

        income.put("productNo", Okhttp.getPropertiesVal("productId"));
        income.put("applyId",creditApplyId);
        income.put("monthSalary",10000.00); //月收入 必传

        return income;
    }

    public static JSONObject assetLiabilityRatioQuery(String productNo,String mainapplyId,String applyId){
        JSONObject query = publicField;

        query.put("productNo",productNo);
        query.put("mainapplyId",mainapplyId);
        query.put("applyId",applyId); //主申请单号

        return query;
    }

    public static void main(String[] args) {
        JSONObject info = JSON.parseObject(getProVal("preCreditApplySave"));
//        assetLiabilityRatioQuery("F0220",info.getString("mainCreditApplyId"),
//                info.getString("creditApplyId"));

        incomeVerify(info.getString("creditApplyId"));
    }
}
