package data.credit_loan_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

public class MortgageRemouldData {


    private static final String date = Okhttp.getCurrentDate();
    private static final JSONArray accessory = MortgageData.attachments();

    private static final JSONArray dataList(){
        JSONObject list = new JSONObject();
        list.put("loanApplyId","");

        JSONArray array = new JSONArray();
        array.add(list);
        return array;
    }

    //确认贷款后抵押管理系统回调 OR 签章回调
    public static JSONObject callback(String userId){

        JSONObject callback = new JSONObject();
        callback.put("dataList",dataList()); // 必传
        callback.put("compactList", PublicFunc.contract()); // 必传

        return callback;
    }

    //支用抵押岗处理结束后推送信贷
    public static JSONObject pushCreditLoan(){
        JSONObject contractList = new JSONObject();
        contractList.put("blankContractId","");  //必传 空白合同流水号
        contractList.put("deptOfHousingMgtId",""); //必传 房管局id
        contractList.put("deptOfHousingMgtName","");
        contractList.put("contractNo","");
        contractList.put("contractTypeId","");
        contractList.put("contractType","");
        contractList.put("contractName","");
        contractList.put("userId","");
        contractList.put("printTime",date);
        contractList.put("relevanceTime",date);
        contractList.put("businessId","");

        JSONArray array = new JSONArray();
        array.add(contractList);

        JSONObject mortInfo = new JSONObject();
        mortInfo.put("mortgageRegistrationOrgan","锦程消费金融");
        mortInfo.put("mortgageRegistrationDate",date);
        mortInfo.put("contractSignDate",date);

        JSONArray info = new JSONArray();
        info.add(mortInfo);

        JSONObject conInfoList = new JSONObject();
        conInfoList.put("applyId","");

        JSONArray list = new JSONArray();
        list.add(conInfoList);

        JSONObject mainBody = new JSONObject();
        mainBody.put("mainLoanApplyId","");
        mainBody.put("CreditBlankContractDto",array);
        mainBody.put("CreditPaperContractRecordRelationReqDto",accessory);
        mainBody.put("MortgageRegistrationReqDto",info);
        mainBody.put("attachments",PublicFunc.attachments());
        mainBody.put("mortgageContractDtos",list);
        mainBody.put("ReceiveItemWarrantReqDto ",MortgageData.hisRight());
        mainBody.put("alieniIurisPictAttachment",accessory);
        mainBody.put("alieniIurisVideoAttachment",accessory);
        mainBody.put("warrantSurveyPictAttachment",accessory);
        mainBody.put("warrantSurveyVideoAttachment",accessory);
        mainBody.put("otherVideoAttachment",accessory);

        return mainBody;
    }

    //放款初审资料补充
    public static JSONObject loanFirstTrialInfoSupp(){
        JSONObject info = new JSONObject();
        info.put("dataList",dataList());
        info.put("attachments",accessory);

        return info;
    }

    //打回销售补录 OR 企业站补录
    public static JSONObject sellSupplement(){
        JSONObject sell = new JSONObject();
        sell.put("dataList",dataList());

        return sell;
    }

    //抵押岗作废合同
    public static JSONObject MortCancellationContract(){

        JSONObject list = new JSONObject();
        list.put("loanApplyId","");
        list.put("obsoleteReasonCode",""); //原因代码
        list.put("remark","测试废弃");

        JSONArray array = new JSONArray();
        array.add(list);

        JSONObject cancel = new JSONObject();
        cancel.put("dataList", array);
        cancel.put("attachments",accessory);

        return cancel;
    }



}
