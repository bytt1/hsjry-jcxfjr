package data.system_admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Okhttp;

import java.io.*;


public class FileAdminData {

    //影像文件上传
    public static JSONObject imgUpload(String filePath) throws IOException {
        JSONObject img = new JSONObject();
        img.put("tenantId","000");
        img.put("channelNo","06");
        img.put("merchantId","");
        img.put("organId","");
        img.put("empNo","");
        img.put("requestSerialNo", Okhttp.getUUid()); //必传
        img.put("requestTime",Okhttp.getCurrentDateTime()); //必传
        img.put("file",new FileInputStream(filePath)); //必传

        return img;
    }

    //批量上传
    public static JSONObject batchUpload(String[] filePath) throws IOException {
        JSONArray array = new JSONArray();
        for (String path : filePath){
            JSONObject images = new JSONObject();
            images.put("file", new FileInputStream(path));
            array.add(images);
        }



        JSONObject batch = new JSONObject();
        batch.put("tenantId","000");
        batch.put("channelNo","06");
        batch.put("merchantId","");
        batch.put("organId","");
        batch.put("empNo","");
        batch.put("token","");
        batch.put("requestSerialNo","");
        batch.put("requestTime",Okhttp.getCurrentDateTime());
        batch.put("fileList",array);

        return batch;
    }

    public static void main(String[] args) throws IOException {
//        String[] str = new String[]{"C:\\Users\\Administrator\\Pictures\\certificate\\bank.jpg","C:\\Users\\Administrator\\Pictures\\certificate\\face.jpg"};
//        System.out.println(batchUpload(str));

        String url = Okhttp.getDomain() + "/v1/file/upload";
        Okhttp.analysisToJson(Okhttp.doPost(url,
                Okhttp.requestBody(imgUpload("C:\\Users\\Administrator\\Pictures\\certificate\\bank.jpg")).toJSONString()));
    }
}
