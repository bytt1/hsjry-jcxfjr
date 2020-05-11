package business_logic;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import request.credit_loan_admin.IncomingPartsPort;
import request.custom_admin_bus.AccountBinding;
import request.custom_admin_bus.UserLogin;
import utils.Okhttp;
import utils.SqlUtils;

import java.io.IOException;
import java.sql.SQLException;


public class ConfirmLoanAndContractSign {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        UserLogin.loginByAccount("13766974019","123456");

//        IncomingPartsPort.confirmLoan("000CA2020050000000585","000CA2020050000000587",
//                "6212263021168941071", PublicFunc.getUserId("13766974019"));

        String loanApplyId_query_sql = "select loan_apply_id from credit_loan_apply where" +
                " credit_apply_id = \"000CA2020050000000587\"";
//
        String loanApplyId = SqlUtils.select(loanApplyId_query_sql);
        JSONObject contract = IncomingPartsPort.queryContractModule(loanApplyId)
                .getJSONObject("body")
                .getJSONArray("contractList")
                .getJSONObject(0);

        IncomingPartsPort.signLoanContract(PublicFunc.getUserId("13766974019"),
                loanApplyId,"245253",contract);



    }
}
