package data.credit_loan_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.FileUtils;
import utils.Okhttp;

public class IncomingPartsData {




    //共同借款进度查询
    public static JSONObject coBorApplyScheduleQuery(String userId){
        JSONObject json = new JSONObject();
        json.put("userId",userId);
        json.put("thirdUserId","");
        return json;
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
        pre.put("officerCardNumber",Okhttp.fourRandom()); //军官证
        pre.put("usedName", identityDiscernResp.getString("userName")); //曾用姓名
        pre.put("compactList",PublicFunc.contract());
        pre.put("attachments", PublicFunc.attachments());
        pre.put("extraData",new JSONObject());

        return pre;
    }

    //房产信息管理
    public static JSONObject houseInfoManager(String creditApplyId){
        JSONObject house = new JSONObject();
        house.put("userId","");
        house.put("thirdUserId","");
        house.put("creditApplyId",creditApplyId);
        house.put("ownershipCertificateId",Okhttp.fourRandom() + Okhttp.withinSixHundred()); //房屋产权证号
        house.put("ownHouseFlag","1"); //是否本人房产
        house.put("provinceCode","230000");
        house.put("cityCode","231000");
        house.put("areaCode","231025");
        house.put("address","黑龙江省牡丹江市");
        house.put("villageName","hch楼盘");
        house.put("usableArea","120");
        house.put("housingProperty","02"); //房产性质
        house.put("sequenceOfMortgage","01"); //顺位信息
        house.put("landuseRightObtainedWay","01");//土地使用权取得方式
        house.put("firstBankValuation",1300000.00f);//第一顺位银行评估价值

        return house;
    }

    //抵押相关信息查询
    public static JSONObject morCorrInfoQuery(String creditApplyId){
        JSONObject mortgage = new JSONObject();

        mortgage.put("creditApplyId",creditApplyId);

        return mortgage;
    }

    //登记婚姻信息
    public static JSONObject regMarriageInfo(String creditApplyId,String certNo,String spousePhone,String userId){
        JSONObject reg = new JSONObject();
        reg.put("userId",userId);
        reg.put("creditApplyId",creditApplyId);
        reg.put("mobileNo",spousePhone); //必传
        reg.put("marriageStatus","20");
        reg.put("spouseName",Okhttp.getFont(3)); //配偶姓名
        reg.put("spouseCertNo",certNo);
        reg.put("spouseMobileNo",spousePhone);
        reg.put("coBorrowerFlag","0"); //是否最高借款人
        reg.put("propertyOwerFlag","0"); //是否产权人


        FileUtils.writerIdentityInfo("婚姻信息登记",reg);
        return reg;
    }

    //关联人信息查询
    public static JSONObject correlationInfoQuery(String mainCreditApplyId){
        JSONObject corr = new JSONObject();
        corr.put("userId","");
        corr.put("thirdUserId","");
        corr.put("mainCreditApplyId",mainCreditApplyId);
        corr.put("duplicateFlag ","1");

        return corr;
    }

    //添加关联人
    public static JSONObject addCorrelationPerson(String mainCrtAlyId,String certNo,String uid){


        JSONObject correlation = new JSONObject();
        correlation.put("affiliatedForm","0");  //关联人大类 0共同借款人 1抵押人 2主借人
        correlation.put("affiliatedType","04");
        correlation.put("marriageStatus","20");
        correlation.put("userName",Okhttp.getFont(3));
        correlation.put("certificateNo",certNo);
        correlation.put("mortgagorSign","1"); //是否产权人
        correlation.put("spouseName",Okhttp.getFont(3));
        correlation.put("spouseCertificateNo",PublicFunc.getCertNo());
        correlation.put("spouseAffiliatedType","05"); //关联关系
        correlation.put("spouseIsCoBorrower","0");  //是否标识 0否 1是
        correlation.put("spouseMortgagorSign","0");

        JSONArray array = new JSONArray();
        array.add(correlation);

        JSONObject person = new JSONObject();
        person.put("userId","");
        person.put("thirdUserId","");
        person.put("mainBorrowerApplyId",mainCrtAlyId);
        person.put("mainBorrowerId",uid);
        person.put("dataList",array);


        FileUtils.writerIdentityInfo("添加关联人",person);
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

        JSONObject person = new JSONObject();
        person.put("userId","");
        person.put("thirdUserId","");
        person.put("mainBorrowerApplyId","000CA2020040000000308");
        person.put("mainBorrowerId","000UC020000729830");
        person.put("dataList",array);


        return person;
    }

    //删除关联人
    public static JSONObject deleteCorrelationPerson(String CreditApplyId,String corrId){
        JSONObject del = new JSONObject();
        del.put("userId","");
        del.put("thirdUserId","");
        del.put("mainCreditApplyId",CreditApplyId);
        del.put("affiliatedId",corrId); //关联编号

        return del;
    }

    //提交预授信信息
    public static JSONObject commitPreCreditInfo(String creditApplyId,float amount,
                                                 String loanPeriod){
        JSONObject commit = new JSONObject();
        commit.put("userId","");
        commit.put("thirdUserId","");
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
        JSONObject query = new JSONObject();
        query.put("userId","");
        query.put("thirdUserId","");
        query.put("productNo",productId);
        query.put("mainCreditApplyId",mainCreditApplyId);

        return query;
    }

    //其他关联人列表查询
    public static JSONObject otherCorrelationPersonListQuery(String productId,
                                                             String mainCreditApplyId){
        JSONObject other = new JSONObject();
        other.put("userId","");
        other.put("thirdUserId","");
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

        JSONObject cmt = new JSONObject();
        cmt.put("userId","");
        cmt.put("thirdUserId","");
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
        JSONObject query = new JSONObject();
        query.put("userId","");
        query.put("thirdUserId","");
        query.put("applyId",applyId);

        return query;
    }

    //补充资料提交
    public static JSONObject supplementInformationCmt(String productId,String creditApplyId,
                                                      JSONObject realName,String userId,String relationType){
        JSONObject cmt = new JSONObject();
        cmt.put("thirdUserId","");
        cmt.put("userId",userId);
        cmt.put("productId",productId);//
        cmt.put("creditApplyId",creditApplyId);//
        cmt.put("userName",realName.getString("userName"));//
        cmt.put("certificateKind",realName.getString("certType"));//
        cmt.put("certificateNo",realName.getString("certNo"));//
        cmt.put("applyCreditAmount",200000.00);
        cmt.put("customerManagerId","");
        cmt.put("merchantId",Okhttp.getProVal("merchantId"));//
        cmt.put("attachments",PublicFunc.staySupplementsInfo(relationType));
        cmt.put("extraData",PublicFunc.extendInfoList());
        cmt.put("contacts ",PublicFunc.correlationPerList());
        cmt.put("liveAddrHourseCertIsSame ","1");
        cmt.put("liveProvinceCode","230000");
        cmt.put("liveCityCode","231000");
        cmt.put("liveAreaCode","231025");
        cmt.put("liveAddress","成都市高升桥264");
        cmt.put("workName","锦程消费金融");
        cmt.put("workAddrProvinceCode","230000");
        cmt.put("workAddrCityCode","231000");
        cmt.put("workAddrAreaCode","231025");
        cmt.put("workAddrAddress","中航城市广场910");
        cmt.put("highestEducation","5");
        cmt.put("income",20000.00f);
        cmt.put("companyIndustry","2");


        return cmt;
    }

    //确认借款
    public static JSONObject confirmLoan(String maiCreditApplyId,String creditApplyId,String bankCard,String userId){
        JSONObject confirm = new JSONObject();
        confirm.put("userId",userId);
        confirm.put("thirdUserId","");
        confirm.put("productId",PublicFunc.getProductID());
        confirm.put("mainCreditApplyId",maiCreditApplyId);
        confirm.put("creditApplyId",creditApplyId);
        confirm.put("applyAmount",10000.00); //借款本金
        confirm.put("applyTerm",12); // 必传 借款期限
        confirm.put("applyTermUnit","4"); // 必传  借款期限单位
        confirm.put("repayMethod","1");  // 必传 还款方式
        confirm.put("loanPurpose","6");
        confirm.put("repayDay","12");
        confirm.put("collectionRepaymentCardNo",bankCard);
        confirm.put("payLoanAccountId",bankCard);
        confirm.put("customerManagerId","");
        confirm.put("merchantId",Okhttp.getProVal("merchantId"));
        confirm.put("storeId",Okhttp.getProVal("storeId"));
        confirm.put("attachments",PublicFunc.attachments());
        confirm.put("compactList",PublicFunc.loanContract());

        return confirm;
    }

    //合同模板查询
    public static JSONObject contractModule(String loanApplyId){
        JSONObject contract = new JSONObject();
        contract.put("userId","");
        contract.put("thirdUserId","");
        contract.put("loanApplyId",loanApplyId);

        return contract;
    }

    //签署借款合同
    public static JSONObject signLoanContract(String userId,String loanApplyId,
                                              String tradePwd,JSONObject preViewContract){

        JSONObject list = new JSONObject();
        list.put("contractId",preViewContract.getString("contractId"));
        list.put("contractType",preViewContract.getString("contractType"));
        list.put("contractUrl",preViewContract.getString("contractUrl"));

        JSONArray array = new JSONArray();
        array.add(list);

        JSONObject sign = new JSONObject();
        sign.put("userId",userId);
        sign.put("thirdUserId","");
        sign.put("loanApplyId",loanApplyId);
        sign.put("tradePassword",tradePwd);
        sign.put("contractList",array);

        return sign;
    }

    //取消借款
    public static JSONObject cancelLoan(){
        JSONObject cancel = new JSONObject();
        cancel.put("userId","");
        cancel.put("thirdUserId","");
        cancel.put("productNo","");
        cancel.put("mainCreditApplyId","");

        return cancel;
    }

    //待补签合同信息查询
    public static JSONObject staySupplementContractInfoQuery(){
        JSONObject sup = new JSONObject();
        sup.put("userId","");
        sup.put("thirdUserId","");
        sup.put("productId","");

        return sup;
    }

    //补签合同
    public static JSONObject supplementarySignatureContract(){
        JSONObject signature = new JSONObject();
        signature.put("userId","");
        signature.put("thirdUserId","");
        signature.put("productId","");
        signature.put("compactList",PublicFunc.contract());;

        return signature;
    }

    //关联人合同签署授权
    public static JSONObject corrContractSignatureAuth(){
        JSONObject conSign = new JSONObject();
        conSign.put("userId","");
        conSign.put("thirdUserId","");
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
        JSONObject json = new JSONObject();
        json.put("userId","");
        json.put("thirdUserId","");
        return json;
    }

    //房管局列表查询
    public static JSONObject deptOfHousingManagementListQuery(){
        JSONObject dept = new JSONObject();
        dept.put("userId","");
        dept.put("thirdUserId","");
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
        JSONObject deadline = new JSONObject();
        deadline.put("userId","");
        deadline.put("thirdUserId","");
        deadline.put("mainCreditApplyId",mainApplyId);
        deadline.put("creditApplyId",creditApplyId);

        return deadline;
    }

    //开具结清证明
    public static JSONObject settleProve(){
        JSONObject prove = new JSONObject();
        prove.put("userId","");
        prove.put("thirdUserId","");
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

        JSONObject upload = new JSONObject();
        upload.put("userId","");
        upload.put("thirdUserId","");
        upload.put("loanInvoiceId","");
        upload.put("attachments",array);

        return upload;
    }
}
