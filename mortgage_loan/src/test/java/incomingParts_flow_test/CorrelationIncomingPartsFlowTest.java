package incomingParts_flow_test;

import business_logic.CorrelationIncomingPartsFlow;
import data.publicdata.PublicFunc;
import org.testng.annotations.Test;
import utils.SqlUtils;
import java.sql.ResultSet;
import java.sql.SQLException;


@Test(groups = "correlation")
public class CorrelationIncomingPartsFlowTest {

    private final String mainMobileNo = PublicFunc.getMobileNo();

    @Test
    public void runTest(){

        String sql = "SELECT certificate_no,user_name FROM credit_affiliated_info WHERE" +
                " main_borrower_id = (SELECT user_id FROM dev_user.user_contact_station_info WHERE" +
                " station_id = (SELECT station_id FROM dev_user.user_telephone_info WHERE" +
                " telephone = \""+mainMobileNo+"\" LIMIT 1)) and ISNULL(affiliated_apply_id) order by update_time desc";

        CorrelationIncomingPartsFlow flow = new CorrelationIncomingPartsFlow();

        try {
            ResultSet result = SqlUtils.selectAll(sql,"dev_credit");
            while (result.next()){
                flow.userName = result.getString("user_name");
                flow.certNo = result.getString("certificate_no");
                flow.customRegister();
                flow.customRegInfoQuery();
                flow.realNameAuth();
                flow.preCreditApplySave();
                flow.loanChoiceDeadlineQuery();
                flow.commitPredictCreditInfo();
                flow.staySupplementInfoQuery();
                flow.supplementInfoCommit();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

}
