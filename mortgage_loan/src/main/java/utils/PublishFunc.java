package utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public final class PublishFunc {

    private static final Logger log = LoggerFactory.getLogger(PublishFunc.class);

    public static void assertFunc(JSONObject resp){
        if (resp == null || resp.containsKey("errorMsg")){
            log.info("系统异常 >> ");
            Assert.fail();
        }

        Assert.assertEquals(resp.getString("tmpRspMsg"),"成功");
    }
}
