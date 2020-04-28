package request.credit_loan_admin;

import com.alibaba.fastjson.JSONObject;
import data.credit_loan_admin.ContractData;
import utils.Okhttp;

import java.io.IOException;

public class ContractManagement {

    private static final String DOMAIN = Okhttp.getDomain();
    private static final String DETAIL_QUERY_URL = DOMAIN + "/v1/compact/queryCompactDetail";
    private static final String LIST_QUERY_URL = DOMAIN + "/v1/contract/queryContractList";
    private static final String MODULE_INFO_QUERY_URL = DOMAIN + "/v1/compact/queryCompactInfo";

    //合同模板详情查询
    public static JSONObject contractModuleDetailQuery(String compactId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(DETAIL_QUERY_URL,
                Okhttp.requestBody(ContractData.templateDetailQuery(compactId)).toJSONString()));
    }

    //body数据全部为0
    public static JSONObject compactListQuery() throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(LIST_QUERY_URL,
                Okhttp.requestBody(ContractData.templateListQuery()).toJSONString()));
    }

    public static JSONObject compactModuleInfoQuery(String type,String proId) throws IOException {
        return Okhttp.analysisToJson(Okhttp.doPost(MODULE_INFO_QUERY_URL,
                Okhttp.requestBody(ContractData.templateInfoQuery(type, proId)).toJSONString()));
    }

    //无返回body
    public static void main(String[] args) throws IOException {
//        contractModuleDetailQuery("4199769758219567509");
//        compactListQuery();
        compactModuleInfoQuery("2","");
    }
}
