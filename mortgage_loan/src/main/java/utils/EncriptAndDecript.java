package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class EncriptAndDecript {

    private static final String encriptUrl = Okhttp.getDomain() + "/pkt/tool/encrypt";
    private static final String decriptUrl = Okhttp.getDomain() + "/pkt/tool/decrypt";

    private static Logger log = LoggerFactory.getLogger(EncriptAndDecript.class);

    //授信数据加密
    public static String encript(String data) throws IOException {

        return Okhttp.analysisToStr(Okhttp.doPost(encriptUrl,data));
    }

    //授信数据解密
    public static String decript(String data) throws IOException {

        return Okhttp.analysisToStr(Okhttp.doPost(decriptUrl,data));
    }


}
