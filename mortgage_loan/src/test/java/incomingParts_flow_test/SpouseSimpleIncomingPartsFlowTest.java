package incomingParts_flow_test;


import business_logic.SpouseIncomingPartsFlow;
import org.testng.annotations.Test;


@Test(groups = {"registerMarriageInfo"})
public class SpouseSimpleIncomingPartsFlowTest {


    @Test
    public void runTest(){
        SpouseIncomingPartsFlow flow = new SpouseIncomingPartsFlow();
        flow.beforeClass();
        flow.customRegister();
        flow.customRegInfoQuery();
        flow.realNameAuth();
        flow.preCreditApplySave();
    }


}
