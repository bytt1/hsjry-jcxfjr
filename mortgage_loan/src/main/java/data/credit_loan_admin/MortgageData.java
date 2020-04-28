package data.credit_loan_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;

public class MortgageData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    public static JSONArray attachments(){
        JSONObject attachments = new JSONObject();
        attachments.put("fileKind",1); // 必传
        attachments.put("fileUrl", Okhttp.getPropertiesVal("fileUrl")); // 必传
        attachments.put("fileName","2020010207"); // 必传
        attachments.put("fileType","");

        JSONArray array = new JSONArray();
        array.add(attachments);

        return array;
    }

    public static JSONArray contract(){
        JSONObject contract = new JSONObject();
        contract.put("contractType","16"); // 必传
        contract.put("contractName", "抵押登记合同"); // 必传
        contract.put("url",Okhttp.getPropertiesVal("fileUrl")); // 必传
        contract.put("fileName","合同影像");

        JSONArray array = new JSONArray();
        array.add(contract);

        return array;
    }

    public static JSONArray hisRight(){
        JSONObject save = publicField;

        save.put("guaranteeId",""); //担保编号
        save.put("mortgageId","");
        save.put("warrantName",""); //权证名称
        save.put("warrantType","");
        save.put("numOfRealEstateRegCert",""); //不动产登记权证号
        save.put("obligeeName",""); //权利人
        save.put("obligorName",""); //义务人
        save.put("location","成都市抚琴小区");
        save.put("warrantUnitNo","3-1-101");
        save.put("mortgageWay",""); //抵押方式
        save.put("debtAmount",""); //权债数额
        save.put("debtTimeLimit",Okhttp.getCurrentDate());
        save.put("sequenceOfMortgage",""); //抵押顺位信息
        save.put("businessNo","");
        save.put("realPropertyCertificateNo",""); //不动产证明编号
        save.put("certificationDate",Okhttp.getCurrentDate());
        save.put("provinceCode","51");
        save.put("cityCode","01");
        save.put("areaCode","00");
        save.put("remark","备注");

        JSONArray array = new JSONArray();
        array.add(save);

        return array;
    }

    //待办工单列表查询
    public static JSONObject backlogWorkOrderListQuery(){
        JSONObject backlog = publicField;

        backlog.put("applyId","");
        backlog.put("customerId","");
        backlog.put("customerName","");
        backlog.put("certificateNo","");
        backlog.put("taskNode","");
        backlog.put("pageNum","1");  //必传
        backlog.put("pageSize","10");  //必传

        return backlog;
    }

    //有效未使用合同流水号查询
    public static JSONObject validNotReceiveContractSerialNoQuery(){
        JSONObject serial = publicField;

        serial.put("deptOfHousingMgtId","");
        serial.put("contractType","");
        serial.put("blankContractStatus","01");
        serial.put("loanApplyId","");


        return serial;
    }

    public static void main(String[] args) throws IOException {
        String url = Okhttp.getDomain() + "/v1/credit/queryContractNoList";
        Okhttp.analysisToJson(Okhttp.doPost(url,
                validNotReceiveContractSerialNoQuery().toJSONString()));
    }

    //作废合同
    public static JSONObject cancellationContract(){

        JSONObject cancellation = publicField;

        cancellation.put("mainLoanApplyId","");
        cancellation.put("deptOfHousingMgtId","");
        cancellation.put("obsoleteReasonCode",""); //作废原因码
        cancellation.put("remark","");
        cancellation.put("attachments",attachments());

        return cancellation;
    }

    //作废合同流水号
    public static JSONObject cancellationContractSerialNo(){

        JSONObject contractId = new JSONObject();
        contractId.put("blankContractId",""); //合同流水号
        contractId.put("obsoleteReasonCode","");  //作废原因码
        contractId.put("remark","");

        JSONArray array = new JSONArray();
        array.add(contractId);

        JSONObject serial = publicField;

        serial.put("mainLoanApplyId","");
        serial.put("blankContractIdList ",array);
        serial.put("attachments",attachments());

        return serial;
    }

    //关联合同
    public static JSONObject relevanceContract(String userId){
        JSONObject relevance = publicField;

        relevance.put("creditApplyId","");
        relevance.put("contractList ", PublicFunc.contract());

        return relevance;
    }

    //办理抵押登记
    public static JSONObject transactMortgageReg(){
        JSONObject transact = publicField;

        transact.put("mainLoanApplyId","");
        transact.put("mortgageRegistrationOrgan","锦程消费金融");
        transact.put("mortgageRegistrationDate",Okhttp.getCurrentDate());
        transact.put("contractSignDate","");
        transact.put("attachments",attachments());
        transact.put("contractAttachements",contract());

        return transact;
    }

    //贷款信息查询
    public static JSONObject loanInfoQuery(){
        JSONObject query = publicField;

        query.put("loanApplyId","");

        return query;
    }

    //保存见他权数据
    public static JSONObject saveHisRightData(){
        JSONObject save = publicField;

        save.put("guaranteeId",""); //担保编号
        save.put("mortgageId","");
        save.put("warrantName",""); //权证名称
        save.put("warrantType","");
        save.put("numOfRealEstateRegCert",""); //不动产登记权证号
        save.put("obligeeName",""); //权利人
        save.put("obligorName",""); //义务人
        save.put("location","成都市抚琴小区");
        save.put("warrantUnitNo","3-1-101");
        save.put("mortgageWay",""); //抵押方式
        save.put("debtAmount",""); //权债数额
        save.put("debtTimeLimit",Okhttp.getCurrentDate());
        save.put("sequenceOfMortgage",""); //抵押顺位信息
        save.put("businessNo","");
        save.put("realPropertyCertificateNo",""); //不动产证明编号
        save.put("certificationDate",Okhttp.getCurrentDate());
        save.put("provinceCode","51");
        save.put("cityCode","01");
        save.put("areaCode","00");
        save.put("remark","备注");
        save.put("alieniIurisPictAttachment",attachments());
        save.put("alieniIurisVideoAttachment",attachments());
        save.put("warrantSurveyPictAttachment",attachments());
        save.put("warrantSurveyVideoAttachment",attachments());
        save.put("otherVideoAttachment",attachments());

        return save;
    }

    //库存资料查询
    public static JSONObject inventoryInformationQuery(){
        JSONObject inventory = publicField;

        inventory.put("deptOfHousingMgtId","");

        return inventory;
    }

    //申请进度查询
    public static JSONObject applyScheduleQuery(){
        JSONObject query = publicField;

        query.put("contractNo","");
        query.put("serialNo","");
        query.put("pageNum","1");
        query.put("pageSize","10");

        return query;
    }
}
