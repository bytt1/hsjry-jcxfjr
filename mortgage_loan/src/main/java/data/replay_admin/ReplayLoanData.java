package data.replay_admin;

import com.alibaba.fastjson.JSONObject;
import utils.Okhttp;

public class ReplayLoanData {

    //汇总查询
    public static JSONObject stayReplayCollectQuery(String userId){
        JSONObject collect = new JSONObject();
        collect.put("userId",userId);
        collect.put("thirdUserId","");
        return collect;
    }

    //待还款账单明细查询
    public static JSONObject stayReplayDetailsQuery(String userId,String loanInvoiceId){
        JSONObject collect = new JSONObject();
        collect.put("userId",userId);
        collect.put("thirdUserId","");
        collect.put("loanInvoiceId",loanInvoiceId);
        return collect;
    }

    //普通还款试算
    public static JSONObject commonReplayTrial(String loanInvoiceId,String amount){
        JSONObject trial = new JSONObject();
        trial.put("loanInvoiceId",loanInvoiceId);
        trial.put("discountIds","");
        trial.put("amount",amount);
        return trial;
    }

}
