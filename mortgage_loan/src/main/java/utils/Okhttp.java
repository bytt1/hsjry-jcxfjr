package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.util.Strings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Okhttp {

    private static final Logger log = LoggerFactory.getLogger(Okhttp.class);
    private static final long timeOut =  30;
    private static final String  PATH = "src/main/resources/profile.properties";
    private static SimpleDateFormat format;



    public static void writerPro(JSONObject json,String keyName,boolean clearFlag){
        Properties pro = new Properties();
        if (clearFlag){
            try {
                removeByKey(keyName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        pro.setProperty(keyName,json.toJSONString());
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(PATH,true), StandardCharsets.UTF_8)) {
            pro.store(writer,null);
            log.info("响应数据已写入文件 >>> ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writerFlag(JSONObject json,String keyName,boolean clearFlag){
        if ("成功".equals(json.getJSONObject("head").getString("returnMessage"))){
            writerPro(json.getJSONObject("body"),keyName,clearFlag);
            log.info("写入返回数据 >>> ");
        }
    }

    private static void removeAll() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PATH));
        for (String str : properties.stringPropertyNames()){
            properties.remove(str);
        }
        properties.store(new OutputStreamWriter(new FileOutputStream(PATH)
                ,StandardCharsets.UTF_8),"");

    }

    public static void removeByKey(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PATH));
        if (properties.containsKey(key)) {
            properties.remove(key);
            properties.store(new OutputStreamWriter(new FileOutputStream(PATH)
                    ,StandardCharsets.UTF_8),"");
        }
    }

    public static void removeByKeys(String[] keys) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PATH));

        for (String str : keys){
            properties.remove(str);
        }
        properties.store(new OutputStreamWriter(new FileOutputStream(PATH,true),
                StandardCharsets.UTF_8),"");

    }

    public static String getProVal(String key) {
        Properties pro = new Properties();
        try {
            pro.load(new InputStreamReader(new FileInputStream(PATH),StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty(key);
    }

    public static String getPropertiesVal(String key){
        Properties pro = new Properties();
        try {
            pro.load(new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty(key);
    }

    public static void setPro(String key,String val){
        isContainsKey(key);
        Properties pro = new Properties();
        try {
            pro.setProperty(key, val);
            pro.store(new OutputStreamWriter(new FileOutputStream(PATH,true)
                            , StandardCharsets.UTF_8)
                    , "");
        } catch (IOException e) {
                e.printStackTrace();
        }

    }

    private static void isContainsKey(String key){
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(PATH),StandardCharsets.UTF_8));
            if (properties.containsKey(key)){
                removeByKey(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String font() {
        String str = "";
        int hightPos;
        int lowPos;
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        return str;
    }

    public static String getFont(int num) {
        String s = "";
        for(int i = 0; i < num;i++) {
            s += font();
        }
        return s;
    }



    /**
     * 从配置文件中获取domain的值
     * **/
    public static String getDomain(){
        return getPropertiesVal("domain");
    }


    public static String getCurrentDate(){
        format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    public static String getCurrentDateTime(){
        format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }


    public static String randomStr(){
        Random random = new Random();

        return "" + random.nextInt(99999999);
    }

    /**
     * 创建4位随机数
     * **/
    public static String fourRandom(){
        Random random = new Random();
        return "" + (random.nextInt(8999) + 1000);
    }

    public static String withinSixHundred(){
        Random random = new Random();
        return "" + (random.nextInt(499) + 100);
    }


    /**
     * 创建随机电话号
     * **/
    public static String getPhone(){
        return "137" + fourRandom() + fourRandom();
    }

    /**
     * 创建8位随机数
     * **/
    public static String eightNum(){
        return "" + fourRandom() + fourRandom();
    }

    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String bs64(String filePath) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(b);
    }

    public static JSONObject requestBody(JSONObject body){
        JSONObject full = new JSONObject();
        full.put("head", PublicFunc.dealRequestHead());
        full.put("body",body);

        log.info("请求报文 ---> " + full);
        return full;
    }


    /**
     * 请求客户端
     * **/
    private static OkHttpClient createClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut,TimeUnit.SECONDS)
                .readTimeout(timeOut,TimeUnit.SECONDS);

        return builder.build();
    }

    /**
     * 构造请求header
     * **/
    public static Headers ceateHeaders(){
         Headers.Builder builder = new Headers.Builder();
         builder.add("hsjry-channel-code","01");
         builder.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.100 Safari/537.36");
         builder.add("Content-Type","application/json,text/plain");
         return builder.build();
    }

    public static Headers createTextHeaders(){
        Headers.Builder builder = new Headers.Builder();
        builder.add("Accept","application/json, text/plain, */*");
        builder.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.100 Safari/537.36");
//        builder.add("Accept-Encoding","gzip, deflate, br");
//        builder.add("Accept-Language","zh-CN,zh;q=0.9");
//        builder.add("Connection","keep-alive");
//        builder.add("Content-Length","858");
        builder.add("content-type","application/x-ndjson");
        builder.add("Host","kibana-dev.corp.jccfc.com");
        builder.add("kbn-version","7.2.0");
//        builder.add("Origin","https://kibana-dev.corp.jccfc.com");
//        builder.add("Referer","https://kibana-dev.corp.jccfc.com/app/kibana");
//        builder.add("Sec-Fetch-Dest","empty");
//        builder.add("Sec-Fetch-Mode","cors");
//        builder.add("Sec-Fetch-Site","same-origin");
        return builder.build();
    }

    /**
     *构造无参get的request
     * **/
    private static Request createGetReq(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }

    /**
     *构造带参get的request
     * **/
    private static Request createGetReq(HttpUrl.Builder url){
        return new Request.Builder()
                .url(url.build())
                .build();
    }

    /**
     * 构造post的request
     * **/
    private static Request createPostReq(String url,String json){
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json,type);

        return new Request.Builder()
                .url(url)
                .post(body)
                .headers(ceateHeaders())
                .build();
    }

    private static Request createTextPost(String url,String json){
        MediaType type = MediaType.parse("application/json,text/plain; charset=utf-8");
        RequestBody body = RequestBody.create(json,type);

        return new Request.Builder()
                .url(url)
                .post(body)
                .headers(createTextHeaders())
                .build();
    }
    /**
     * @param url 请求地址
     *  无参GET请求
     * */
    public static Response doGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return createClient().newCall(request).execute();
    }

    /**
     * 带参get请求
     * **/
    public static Response doGet(String url, Map<String,Object> map) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if(map != null) {
            for (Map.Entry<String, Object> param : map.entrySet()) {
                builder.addQueryParameter(param.getKey(), param.getValue().toString());
            }
        }

        return createClient().newCall(createGetReq(builder)).execute();
    }


    /**
     * post请求
     * **/
    public static Response doPost(String url,String data) throws IOException {
        log.info("请求地址 >>> " + url);
        return createClient().newCall(createPostReq(url,data)).execute();
    }

    public static Response doTextPost(String url,String data) throws IOException {
        log.info("请求地址 >>> " + url);
        return createClient().newCall(createTextPost(url,data)).execute();
    }

    /**
     * @param map 同图片上传的参数
     * @param fileField 接口中上传文件字段
     * @param file 上传的图片
     *
     * **/
    public static Response doUpLoadFile(String url,File file,Map<String,Object> map,String fileField) throws IOException {
        MediaType mediaType = MediaType.parse("image/png;image/jpg;image/jpeg; charset=utf-8");
        RequestBody fileBody = RequestBody.create(file,mediaType);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart(fileField,file.getName(),fileBody);
        if (!map.isEmpty()){
            for (Map.Entry<String,Object> param : map.entrySet()){
                builder.addFormDataPart(param.getKey(),param.getValue().toString());
            }
        }
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return createClient().newCall(request).execute();

    }


    public static JSONObject analysisToJson(Response response) throws IOException {
        String str = Objects.requireNonNull(response.body()).string();
        log.info("响应报文 ---> " + str);
        return JSON.parseObject(str);
    }

    public static String  analysisToStr(Response response) throws IOException {
        String str = Objects.requireNonNull(response.body()).string();
        log.info(str);
        return str;
    }

    public static String regExpResp(Response response) throws IOException {
        String code = null;
        JSONObject resp = analysisToJson(response);
        String pattern = "(code).{5}(\\d{6,6})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(resp.toJSONString());

        while (m.find()) {
            code = m.group(2);
            if (Strings.isNotNullAndNotEmpty(code)) {
                break;
            }
        }

        log.info("验证码 >>> " + code);
        return code;
    }



}
