package data.credit_loan_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.FileUtils;
import utils.Okhttp;

public class IncomingPartsData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");


    //共同借款进度查询
    public static JSONObject coBorApplyScheduleQuery(){
        return publicField;
    }

    //预授信申请保存
//    public static JSONObject preCreditApplySave(JSONObject identityDiscernResp,String mainCreditApplyId){
//
//        String gender = identityDiscernResp.getString("gender");
//
//        JSONObject pre = publicField;
//        pre.put("thirdpartId","");
//        pre.put("merchantId","000UC010000850541");
//        pre.put("storeId","000ORG00000000002");
//        pre.put("custMgrId","");
//        pre.put("userName", identityDiscernResp.getString("userName"));
//        pre.put("mainCreditApplyId",mainCreditApplyId);
//        if ("男".equals(gender)){
//            pre.put("gender","1");
//        }else {
//            pre.put("gender","2");
//        }
//        pre.put("birthday", identityDiscernResp.getString("birthday"));
//        pre.put("certificateKind", identityDiscernResp.getString("certType"));
//        pre.put("certificateNo", identityDiscernResp.getString("certNo"));
//        pre.put("certificateValidityPeriod", identityDiscernResp.getString("dueDate"));
//        pre.put("signOffice", identityDiscernResp.getString("signOrgan"));
//        pre.put("nation", identityDiscernResp.getString("nation"));
//        pre.put("pmtAddrProvinceCode","230000");
//        pre.put("pmtAddrCityCode","231000");
//        pre.put("pmtAddrAreaCode","231025");
//        pre.put("pmtAddress", identityDiscernResp.getString("pmtAddress"));
//        pre.put("officerCardNumber",""); //军官证
//        pre.put("usedName", identityDiscernResp.getString("usedName")); //曾用姓名
//        pre.put("compactList",PublicFunc.contract());
//        pre.put("attachments", PublicFunc.attachments());
//        pre.put("extraData",new JSONObject());
//
//        return pre;
//    }

    public static JSONObject preCreditApplySave(JSONObject identityDiscernResp,
                                                String mainCreditApplyId,
                                                String userId){

        String gender = identityDiscernResp.getString("gender");

        JSONObject pre = new JSONObject();
        pre.put("userId",userId);
        pre.put("merchantId","000UC010000850541");
        pre.put("storeId","000ORG00000000002");
        pre.put("custMgrId","");
        pre.put("userName", identityDiscernResp.getString("userName"));
        pre.put("mainCreditApplyId",mainCreditApplyId);
        if ("男".equals(gender)){
            pre.put("gender","1");
        }else {
            pre.put("gender","2");
        }
        pre.put("birthday", "19690222");
        pre.put("certificateKind", identityDiscernResp.getString("certType"));
        pre.put("certificateNo", identityDiscernResp.getString("certNo"));
        pre.put("certificateValidityPeriod", identityDiscernResp.getString("dueDate"));
        pre.put("signOffice", identityDiscernResp.getString("signOrgan"));
        pre.put("nation", identityDiscernResp.getString("nation"));
        pre.put("pmtAddrProvinceCode","230000");
        pre.put("pmtAddrCityCode","231000");
        pre.put("pmtAddrAreaCode","231025");
        pre.put("pmtAddress", identityDiscernResp.getString("pmtAddress"));
        pre.put("officerCardNumber",""); //军官证
        pre.put("usedName", identityDiscernResp.getString("usedName")); //曾用姓名
        pre.put("compactList",PublicFunc.contract());
        pre.put("attachments", PublicFunc.attachments());
        pre.put("extraData",new JSONObject());

        return pre;
    }

    //房产信息管理
    public static JSONObject houseInfoManager(String creditApplyId){
        JSONObject house = publicField;
        house.put("creditApplyId",creditApplyId);
        house.put("ownershipCertificateId",Okhttp.fourRandom()); //房屋产权证号
        house.put("ownHouseFlag","1"); //是否本人房产
        house.put("provinceCode","230000");
        house.put("cityCode","231000");
        house.put("areaCode","231025");
        house.put("address","黑龙江省牡丹江市");
        house.put("villageName","楼盘名java");
        house.put("usableArea","120");
        house.put("housingProperty","02"); //房产性质
        house.put("sequenceOfMortgage","01"); //顺位信息
        house.put("landuseRightObtainedWay","01");//土地使用权取得方式
        house.put("firstBankValuation",1300000.00f);//第一顺位银行评估价值

        return house;
    }

    //抵押相关信息查询
    public static JSONObject morCorrInfoQuery(String creditApplyId){
        JSONObject mortgage = publicField;
        mortgage.put("creditApplyId",creditApplyId);

        return mortgage;
    }

    //登记婚姻信息
    public static JSONObject regMarriageInfo(String creditApplyId,String spousePhone){
        JSONObject reg = publicField;
        reg.put("creditApplyId",creditApplyId);
        reg.put("mobileNo",spousePhone); //必传
        reg.put("marriageStatus","20");
        reg.put("spouseName",Okhttp.getFont(3)); //配偶姓名
        reg.put("spouseCertNo","32128319790629" +  Okhttp.fourRandom());
        reg.put("spouseMobileNo",spousePhone);
        reg.put("coBorrowerFlag","0"); //是否最高借款人
        reg.put("propertyOwerFlag","1"); //是否产权人

        FileUtils.writerIdentityInfo("婚姻信息登记",reg);
        return reg;
    }

    //关联人信息查询
    public static JSONObject correlationInfoQuery(String mainCreditApplyId){
        JSONObject corr = publicField;

        corr.put("mainCreditApplyId",mainCreditApplyId);
        corr.put("duplicateFlag ","1");

        return corr;
    }

    //添加关联人
    public static JSONObject addCorrelationPerson(String mainCrtAlyId,String uid){
        String spouseCertNo = Okhttp.withinSixHundred() + Okhttp.withinSixHundred() + "19700315" +Okhttp.fourRandom();
        String corrCertNo = Okhttp.withinSixHundred() + Okhttp.withinSixHundred() + "19700315" +Okhttp.fourRandom();


        JSONObject correlation = new JSONObject();
        correlation.put("affiliatedForm","0");  //关联人大类 0共同借款人 1抵押人 2主借人
        correlation.put("affiliatedType","5");
        correlation.put("marriageStatus","20");
        correlation.put("userName",Okhttp.getFont(3));
        correlation.put("certificateNo",corrCertNo);
        correlation.put("mortgagorSign","1"); //是否产权人
        correlation.put("spouseName",Okhttp.getFont(3));
        correlation.put("spouseCertificateNo",spouseCertNo);
        correlation.put("spouseAffiliatedType","5"); //关联关系
        correlation.put("spouseIsCoBorrower","0");  //是否标识 0否 1是
        correlation.put("spouseMortgagorSign","1");

        JSONArray array = new JSONArray();
        array.add(correlation);

        JSONObject person = publicField;

        person.put("mainBorrowerApplyId",mainCrtAlyId);
        person.put("mainBorrowerId",uid);
        person.put("dataList",array);


        FileUtils.writerIdentityInfo("userName",person);
        return person;
    }

    //修改关联人
    public static JSONObject editCorrelationPersonInfo(){

        JSONObject correlation = new JSONObject();
        correlation.put("affiliatedId","0");  //关联人大类 0共同借款人 1抵押人 2主借人
        correlation.put("affiliatedType","1");
        correlation.put("marriageStatus","20");
        correlation.put("userName","江桂蓝");
        correlation.put("certificateNo","511502199709278467");
        correlation.put("mortgagorSign","1"); //是否产权人
        correlation.put("spouseName","吴亚洲");
        correlation.put("spouseCertificateNo","422223196902224912");

        JSONArray array = new JSONArray();
        array.add(correlation);

        JSONObject person = publicField;

        person.put("mainBorrowerApplyId","000CA2020040000000308");
        person.put("mainBorrowerId","000UC020000729830");
        person.put("dataList",array);


        return person;
    }

    //删除关联人
    public static JSONObject deleteCorrelationPerson(String CreditApplyId,String corrId){
        JSONObject del = publicField;

        del.put("mainCreditApplyId",CreditApplyId);
        del.put("affiliatedId",corrId); //关联编号

        return del;
    }

    //提交预授信信息
    public static JSONObject commitPreCreditInfo(String creditApplyId,float amount,
                                                 String loanPeriod){
        JSONObject commit = publicField;

        commit.put("merchantId",Okhttp.getPropertiesVal("merchantId"));
        commit.put("storeId",Okhttp.getPropertiesVal("storeId"));
        commit.put("creditApplyId",creditApplyId);
        commit.put("applyAmount",amount);
        commit.put("loanPeriod",loanPeriod);
        commit.put("loanPurpose","6");
        commit.put("afterRepayLoanFlag","0");
        commit.put("compactList",PublicFunc.contract());  //必传

        return commit;
    }

    //一抵信息列表查询
    public static JSONObject firstMortgageInfoListQuery(String productId,String mainCreditApplyId){
        JSONObject query = publicField;

        query.put("productNo",productId);
        query.put("mainCreditApplyId",mainCreditApplyId);

        return query;
    }

    //其他关联人列表查询
    public static JSONObject otherCorrelationPersonListQuery(String productId,
                                                             String mainCreditApplyId){
        JSONObject other = publicField;

        other.put("productId",productId);
        other.put("authorityType","01"); //授权类型 01预授信授权 02签署合同授权
        other.put("mainCreditApplyId",mainCreditApplyId);
        other.put("mainBorrowerFlag","1");

        return other;
    }

    //关联人全部授权后提交授信
    public static JSONObject corrPerAfterAuthCmtCredit(String productId,
                                                       String mainCreditApplyId,
                                                       String userName){
        String date = Okhttp.getCurrentDate();

        JSONObject cmt = publicField;

        cmt.put("productId",productId);
        cmt.put("thirdpartId","000UC010000006268");//商户或合作方编号
        cmt.put("storeId",Okhttp.getPropertiesVal("storeId"));
        cmt.put("custMgrId","");
        cmt.put("userName",userName); //客户姓名
        cmt.put("mainCreditApplyId",mainCreditApplyId);
        cmt.put("creditLoanInvoiceId",Okhttp.getUUid()); //一抵借据号
        cmt.put("firstLoanPayTime",date); //一抵放款时间
        cmt.put("firstLoanAmount",10000.00); //放款金额
        cmt.put("firstLoanRepayAmount",1000.00); //一抵当期还款金额
        cmt.put("firstLoanPreRepayDate",date); //一抵应还日期
        cmt.put("attachments",PublicFunc.attachments());
        cmt.put("extraData",new JSONObject());


        return cmt;
    }

    //待补充附件列表查询
    public static JSONObject staySupplementedAccessoryListQuery(String applyId){
        JSONObject query = publicField;
        query.put("applyId",applyId);

        return query;
    }

    //补充资料提交
    public static JSONObject supplementInformationCmt(String productId,String creditApplyId,
                                                      JSONObject realName,String userId,String relationType){
        JSONObject cmt = publicField;
        cmt.put("userId",userId);
        cmt.put("productId",productId);//
        cmt.put("creditApplyId",creditApplyId);//
        cmt.put("userName",realName.getString("userName"));//
        cmt.put("certificateKind",realName.getString("certType"));//
        cmt.put("certificateNo",realName.getString("certNo"));//
//        cmt.put("applyCreditAmount",10000.00);
//        cmt.put("customerManagerId","");
        cmt.put("merchantId",Okhttp.getProVal("merchantId"));//
        cmt.put("attachments",PublicFunc.staySupplementsInfo(relationType));
//        cmt.put("extraData",PublicFunc.extendInfoList());
//        cmt.put("contacts ",PublicFunc.correlationPerList());
        cmt.put("income",50000);


        return cmt;
    }

    //确认借款
    public static JSONObject confirmLoan(){
        JSONObject confirm = publicField;

        confirm.put("productId","");
        confirm.put("mainCreditApplyId","");
        confirm.put("creditApplyId","");
        confirm.put("applyAmount",""); //借款本金
        confirm.put("applyTerm",""); // 必传 借款期限
        confirm.put("applyTermUnit",""); // 必传  借款期限单位
        confirm.put("repayMethod","");  // 必传 还款方式
        confirm.put("loanPurpose","");
        confirm.put("repayDay","");
        confirm.put("collectionRepaymentCardNo","");
        confirm.put("payLoanAccountId","");
        confirm.put("customerManagerId","");
        confirm.put("merchantId","");
        confirm.put("storeId","");
        confirm.put("attachments",PublicFunc.attachments());
        confirm.put("compactList",PublicFunc.contract());

        return confirm;
    }

    //取消借款
    public static JSONObject cancelLoan(){
        JSONObject cancel = publicField;

        cancel.put("productNo","");
        cancel.put("mainCreditApplyId","");

        return cancel;
    }

    //待补签合同信息查询
    public static JSONObject staySupplementContractInfoQuery(){
        JSONObject sup = publicField;

        sup.put("productId","");

        return sup;
    }

    //补签合同
    public static JSONObject supplementarySignatureContract(){
        JSONObject signature = publicField;

        signature.put("productId","");
        signature.put("compactList",PublicFunc.contract());;

        return signature;
    }

    //关联人合同签署授权
    public static JSONObject corrContractSignatureAuth(){
        JSONObject conSign = publicField;

        conSign.put("productId","");
        conSign.put("creditApplyId","");
        conSign.put("affiliateLoanApplyId","");//支用申请id
        conSign.put("compactList",PublicFunc.contract());

        return conSign;
    }

    //还款计划明细查询
    public static JSONObject repayPlainDetailsQuery(){
        JSONObject repay = new JSONObject();
        repay.put("loanInvoiceId",""); // 必传 借据编号
        repay.put("repayStatus","");

        return repay;
    }

    //借据详情查询
    public static JSONObject loanInvoiceDetailsQuery(){
        JSONObject invoice = new JSONObject();
        invoice.put("loanInvoiceId",""); //  必传 借据编号
        invoice.put("repayKind","");
        invoice.put("status","");
        invoice.put("startDate","");
        invoice.put("endDate","");
        invoice.put("applyStartDate","");
        invoice.put("applyEndDate","");
        invoice.put("pageNum","1");
        invoice.put("pageSize","10");

        return invoice;
    }

    //客户绑定账户信息查询
    public static JSONObject customBindingAccountInfoQuery(){
        return publicField;
    }

    //房管局列表查询
    public static JSONObject deptOfHousingManagementListQuery(){
        JSONObject dept = publicField;

        dept.put("mainCreditApplyId","");
        dept.put("creditApplyId","");
        dept.put("deptOfHousingMgtId","");
        dept.put("provinceCode","51");
        dept.put("cityCode","01");
        dept.put("areaCode","00");
        dept.put("signType","01"); //签约模式  01面签 02电子签
        dept.put("deptOfHousingMgtStatus","01"); //房管局状态 01正常 00失效

        return dept;
    }

    //贷款可选期限查询
    public static JSONObject loanSelectableDeadlineQuery(String mainApplyId,String creditApplyId){
        JSONObject deadline = publicField;

        deadline.put("mainCreditApplyId",mainApplyId);
        deadline.put("creditApplyId",creditApplyId);

        return deadline;
    }

    //开具结清证明
    public static JSONObject settleProve(){
        JSONObject prove = publicField;

        prove.put("email","");
        prove.put("productId",""); // 必传
        prove.put("tradePassword",""); // 必传
        prove.put("certificateKind","0");
        prove.put("certificateNo","");

        return prove;
    }

    //用途凭证上传
    public static JSONObject purposeCertificateUpload(){
        JSONObject attachments = new JSONObject();
        attachments.put("fileKind",1); // 必传
        attachments.put("fileUrl", Okhttp.getPropertiesVal("fileUrl")); // 必传
        attachments.put("fileName","2020010207"); // 必传
        attachments.put("fileType","22");
        attachments.put("loanPurpose","6");

        JSONArray array = new JSONArray();
        array.add(attachments);

        JSONObject upload = publicField;

        upload.put("loanInvoiceId","");
        upload.put("attachments",array);

        return upload;
    }
}
