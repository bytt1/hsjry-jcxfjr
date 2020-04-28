package data.cooperation_tenant;

import com.alibaba.fastjson.JSONObject;
import utils.Okhttp;

public class LoanOffice {

    //授信申请
    public static JSONObject apply(){
        JSONObject credit = new JSONObject();
        credit.put("productId","");  // 必传
        credit.put("qryCreditId","");  // 必传  授信流水
        credit.put("hbUsrNo","");  // 必传
        credit.put("mblNo","");  // 必传
        credit.put("usrIdName","");  // 必传
        credit.put("usrIdCard","");  // 必传
        credit.put("idCardFrontID","");
        credit.put("idCardBackID","");
        credit.put("livePictureID","");
        credit.put("idCardFrontUrl","");
        credit.put("idCardBackUrl","");
        credit.put("livePictureUrl","");
        credit.put("zipCode","");  // 必传
        credit.put("addressCode","");  // 必传
        credit.put("address","");  // 必传
        credit.put("inCome","005");  // 必传
        credit.put("usrJob","");  // 工作类型
        credit.put("contactName","");  // 必传
        credit.put("contactMblNo","");  // 必传
        credit.put("contactRelation","");  // 必传
        credit.put("bankCardNo","");  // 必传
        credit.put("bankCardName","");  // 必传
        credit.put("bankMblNo","");  // 必传
        credit.put("bankCode","");  // 必传
        credit.put("companyAddressCode","");  //
        credit.put("companyAddress","");  //
        credit.put("companyMblNo","");  //
        credit.put("liveOrgNm","");  // 必传
        credit.put("liveOrgId","");  // 必传
        credit.put("liveScore","");  // 必传
        credit.put("schooling","本科");  // 必传
        credit.put("appId","HB");  // 必传
        credit.put("socialIdentity","002");  // 必传
        credit.put("hbScore","");  //
        credit.put("creditTotScore","");  //
        credit.put("creditModScore","");  //
        credit.put("oprId","");  //
        credit.put("oprMblNo","");  //
        credit.put("regDt", Okhttp.getCurrentDate());  // 必传
        credit.put("userType","");  // 用户准入结果
        credit.put("userStarLvl","");  //
        credit.put("applyModelCode","");  //
        credit.put("applyIp","");  //
        credit.put("userMail","");  //
        credit.put("maritalSta","");  // 必传
        credit.put("idExpDt","");  //
        credit.put("country","");  //
        credit.put("nation","");  //
        credit.put("cusSex","");  //
        credit.put("totalBonusAmt","");  // 总红包额
        credit.put("applseq","");  // 签章流水
        credit.put("usrProvNo","");  // 必传 用户归属省份
        credit.put("contactName2","");  //
        credit.put("contactMblNo2","");  //
        credit.put("contactRelation2","");  //
        credit.put("contactName3","");  //
        credit.put("contactMblNo3","");  //
        credit.put("contactRelation3","");  //
        credit.put("actOrgId","");  //
        credit.put("rickScore","");  //


        return credit;
    }

    //授信结果查询
    public static JSONObject applyResultQuery(String qryCreditId,String hbUsrNo){
        JSONObject result = new JSONObject();
        result.put("qryCreditId",qryCreditId); //必传
        result.put("hbUsrNo",hbUsrNo);//必传

        return result;
    }

    //借款申请
    public static JSONObject borrowApply(){
        JSONObject borrow = new JSONObject();
        borrow.put("productId","");
        borrow.put("hbUsrNo","");
        borrow.put("mblNo","");
        borrow.put("brwOrdNo",Okhttp.getUUid());
        borrow.put("brwOrdDt",Okhttp.getCurrentDate());
        borrow.put("pkgName",""); // 非必传
        borrow.put("pkgId","");
        borrow.put("pkgAmt","");
        borrow.put("loanAmt","");
        borrow.put("loanMonth","");
        borrow.put("loanUnit","");
        borrow.put("repayment","");
        borrow.put("loanPurpose",""); //借款用途
        borrow.put("goodId",""); //商品id
        borrow.put("goodNm","");
        borrow.put("oprCusNm",""); // 营业员姓名
        borrow.put("oprId","");
        borrow.put("oprMblNo","");
        borrow.put("depId","");
        borrow.put("depNm","");
        borrow.put("depMngCusNm",""); // 非必传 门店负责人
        borrow.put("depMngMblNo","");
        borrow.put("appId","HB");
        borrow.put("applyModelCode",""); // 非必传 手机串码
        borrow.put("applyIp",""); // 非必传
        borrow.put("mngModel","001");
        borrow.put("busTyp","1");
        borrow.put("depProvNo","51");
        borrow.put("depCityNo","01");
        borrow.put("depRegNo","00");
        borrow.put("mercId",""); // 非必传 门店商户号
        borrow.put("mercNm","");
        borrow.put("actBrwMblNo",""); // 实际借款用户手机号
        borrow.put("actBrwHbUsrNo","");
        borrow.put("actBrwIdNo","");
        borrow.put("actualOrgId",""); // 实际借款出资方编号
        borrow.put("provStgDay",""); // 省份账单日期
        borrow.put("actOrgId",""); // 实际出资方编号

        return borrow;
    }

    //借款申请借查询
    public static JSONObject borrowApplyResultQuery(String brwOrdNo,String brwOrdDt){
        JSONObject result = new JSONObject();
        result.put("brwOrdNo",brwOrdNo); //和包借款订单号
        result.put("brwOrdDt",brwOrdDt); //和包借款订单日期

        return result;
    }

    //还款计划查询
    public static JSONObject repayPlainQuery(String orgOrdNo){
        JSONObject plain = new JSONObject();
        plain.put("orgOrdNo",orgOrdNo); //资金方订单号

        return plain;
    }

    //主动还款
    public static JSONObject initiativeRepayApply(String orgOrdNo){
        JSONObject initiative = new JSONObject();
        initiative.put("orgOrdNo",orgOrdNo); //资金方订单号
        initiative.put("ordMod","1");
        initiative.put("rpyMod","1"); //还款模式 1还末期 2提前清贷 3退货
        initiative.put("rpySeq","1"); //还款模式为1时不能为空
        initiative.put("actRpyAmt",1000.05);

        return initiative;
    }

    //主动还款结果通知
    public static JSONObject initiativeRepayResultNotification(){
        JSONObject notification = new JSONObject();
        notification.put("orgOrdNo","");
        notification.put("ordMod","1");
        notification.put("rpyMod","1");
        notification.put("rpySeq","1");
        notification.put("actRpyAmt",1000.05);
        notification.put("rpyOrdNo",""); //还款订单号
        notification.put("rpyOrdDt",Okhttp.getCurrentDate());
        notification.put("rpySts","S");
        notification.put("isOverdue","0");
        notification.put("buisnessId","001");
        notification.put("rpyDesc","还款成功");
        notification.put("capCorg","");//  非必传 还款卡银行编码
        notification.put("crdNoLast",""); // 非必传 还款卡末4位
        notification.put("acPayTyp","001");// 非必传 扣款方式

        return notification;
    }

    //主动扣款申请
    public static JSONObject initiativeWithHoldApply(){
        JSONObject withHold = new JSONObject();
        withHold.put("brwOrdNo","");
        withHold.put("brwOrdDt","");
        withHold.put("orgOrdNo","");
        withHold.put("ordMod","1");// 订单模式
        withHold.put("rpyMod","1");//  还款模式
        withHold.put("rpySeq",""); // 还款期数
        withHold.put("applyRpyAmt",1000.05); // 申请应还金额
        withHold.put("serviceFee",0.50); // 手续服务费
        withHold.put("lateFee",10.50); // 逾期服务费
        withHold.put("buisnessId","001");

        return withHold;
    }

    //主动还款申请结果查询
    public static JSONObject initiativeWithHoldResultQuery(){
        JSONObject query = new JSONObject();
        query.put("qryModel","0"); // 必传  查询模式  0 - 使用预处理流水号查询
                                    //1 - 使用还款订单号查询

        query.put("rpyPreJrn",""); // 还款预处理流水号
        query.put("rpyPreDt","");
        query.put("rpyOrdNo","");
        query.put("rpyOrdDt","");
        query.put("buisnessId","001");

        return query;
    }

    //套餐办理结果通知
    public static JSONObject comboNotification(){
        JSONObject combo = new JSONObject();
        combo.put("orgOrdNo","");
        combo.put("acpTm",""); //业务办理时间内
        combo.put("modelCode",""); //机型串码编号
        combo.put("appId","HB");
        combo.put("pickCode",Okhttp.eightNum());
        combo.put("oprId",""); //营业员编号
        combo.put("oprMblNo","");
        combo.put("depId",""); //营业厅编号
        combo.put("depNm","");
        combo.put("mblNo","");
        combo.put("groupPictureId",""); //非必传
        combo.put("ticketPictureId",""); //非必传
        combo.put("groupPictureUrl",""); //非必传
        combo.put("ticketPictureUrl",""); //非必传

        return combo;
    }

    //授信额度解冻通知
    public static JSONObject unfreezeNotification(String brwOrdNo,String brwOrdDt){
        JSONObject unfreeze = new JSONObject();
        unfreeze.put("brwOrdNo",brwOrdNo);
        unfreeze.put("brwOrdDt",brwOrdDt);

        return unfreeze;
    }

}
