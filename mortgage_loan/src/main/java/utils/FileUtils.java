package utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static InputStreamReader reader;
    private static BufferedReader bf;
    private static final String filePath = "src/main/resources/identityInfoLog.txt";

    //写入配置文件  已覆盖形式
    public static void writerPro(String key,Object val) throws IOException {

        Properties pro = new Properties();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath,false)
                , StandardCharsets.UTF_8);
        pro.setProperty(key,val.toString());
        pro.store(writer,"");
        writer.close();
        log.info("已写入配置文件 >> ");

    }

    public static String getProVal(String key) throws IOException {
        Properties pro = new Properties();
        pro.load(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        return pro.getProperty(key);
    }

    public static void writerIdentityInfo(String keyName,JSONObject json){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(filePath,true),
                    StandardCharsets.UTF_8);
            writer.append("\r");
            writer.append(format.format(new Date()));
            writer.write(keyName + " = " + json.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        JSONObject j = new JSONObject();
        j.put("哈哈哈","阿斯顿发士大夫");
        writerIdentityInfo("发送到",j);
    }

    //按行读取文件
    public static String readLine(File file) throws IOException {
        reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        bf = new BufferedReader(reader);
        return bf.readLine();
    }

    //读取指定行
    public static String read(File file,int readLine) throws IOException {
        bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int writerLine = Integer.parseInt(FileUtils.getProVal("fileLine"));
        int line = 1;
        String str;

        while ((str = bf.readLine()) != null){
            if (str.trim().equals("")){
                continue;
            }else if (line == readLine){
                writerLine++;
                break;
            }
            line++;
        }
        log.info("第" + line + "行数据 >> " + str);
        writerPro("fileLine",writerLine);
        return str;
    }

    //关闭流
    public static void closeRead(){
        try {
            if (bf != null){
                bf.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetPro() throws IOException {
        writerPro("fileLine",1);
        log.info("markLine.propertie fileLine 已重置");
    }

}
