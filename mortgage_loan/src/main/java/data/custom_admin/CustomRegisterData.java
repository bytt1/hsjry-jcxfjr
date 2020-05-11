package data.custom_admin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
import utils.Okhttp;

import java.io.IOException;

public class CustomRegisterData {

    private static JSONObject publicField = PublicFunc.interfacePublicField("");

    //个人客户手机号注册登录
    public static JSONObject regLogin(String mobileNo) throws IOException {
        JSONObject reg = new JSONObject();
        reg.put("mobileNo", mobileNo); //    必传  客户注册手机号
        reg.put("smsCode",PublicFunc.getCode(Okhttp.getPropertiesVal("logEnv"),mobileNo)); //     必传  短信验证码
        reg.put("password","123456"); //  为空时登录密码未设置
//        reg.put("inviteCode",""); // 邀请码
//        reg.put("inviterTelephone",""); // 邀请人手机号


        return reg;
    }


    //个人客户信息修改
    public static JSONObject editInfo(String userId){

        JSONObject indexOne = new JSONObject();
        indexOne.put("relation","7"); //  关系类型
        indexOne.put("name","hch联系人1"); //  联系人姓名
        indexOne.put("certificateType","0"); //  联系人证件类型  0身份证 3军官证
        indexOne.put("certificateNo",PublicFunc.getCertNo()); //  联系人证件号码
        indexOne.put("mobileNo",Okhttp.getPhone()); //  联系人手机号码

        JSONObject indexTwo = new JSONObject();
        indexTwo.put("relation","7"); //  关系类型
        indexTwo.put("name","hch联系人2"); //  联系人姓名
        indexTwo.put("certificateType","0"); //  联系人证件类型  0身份证 3军官证
        indexTwo.put("certificateNo",PublicFunc.getCertNo()); //  联系人证件号码
        indexTwo.put("mobileNo",Okhttp.getPhone()); //  联系人手机号码

        JSONArray array = new JSONArray();
        array.add(indexOne);
        array.add(indexTwo);

        JSONObject edit = new JSONObject();
        edit.put("userId",userId);
        edit.put("gender","1"); //  1男  2女
        edit.put("education","5"); //
        edit.put("monthSalary",10000); //
        edit.put("marriageStatus","20"); //  婚姻状况  10未婚 20已婚 21初婚 22再婚 23复婚 24有子女 25无子女 30丧偶 40离异 99其他
        edit.put("childrenStatus","1"); //  1无 2有 3求学 4工作
        edit.put("pmtAddrProvinceCode","230000"); // 户籍省份代码
        edit.put("pmtAddrCityCode","231000"); //  户籍城市代码
        edit.put("pmtAddrAreaCode","231025"); //  户籍地区代码
        edit.put("pmtAddress","抚琴小区java"); // 户籍详细地址
        edit.put("liveProvinceCode","230000"); //  居住省份代码
        edit.put("liveCityCode","231000"); //  居住城市代码
        edit.put("liveAreaCode","231025"); //  居住地区代码
        edit.put("liveAddress","抚琴小区java"); //   居住详细地址
        edit.put("houseType","A"); //   住宅类型 A经济适用房
        edit.put("houseState","0"); //  住宅状况
        edit.put("houseProvinceCode","230000"); //  住宅省份代码
        edit.put("houseCityCode","231000"); //   住宅城市代码
        edit.put("houseAreaCode","231025"); //   住宅区域代码
        edit.put("houseAddress","抚琴小区java"); //   住宅地址
        edit.put("workName","锦程消费金融java"); //   单位名称
        edit.put("industryType","1"); //  单位性质
        edit.put("workAddrProvinceCode","230000"); //   单位省份代码
        edit.put("workAddrCityCode","231000"); //  单位城市代码
        edit.put("workAddrAreaCode","231025"); //  单位地区代码
        edit.put("workAddrAddress","高新区中航城市广场java"); //  单位地址
        edit.put("email","1375808564@qq.com"); //  个人电子邮箱
        edit.put("bakMobileNo","13177482453"); //  备用手机号码
        edit.put("contacts",array);//关系人列表



        return edit;
    }

    //客户手机号码修改
    public static JSONObject editPhone(String phone,String code){
        JSONObject edit = new JSONObject();
        edit.put("password","123456");
        edit.put("newMobileNo",phone); //  必传
        edit.put("smsCode",code); //  必传

        return edit;

    }

    //客户头像设置
    public static JSONObject setHeadImg(){
        JSONObject img = new JSONObject();

        img.put("headUrl",Okhttp.getPropertiesVal("fileUrl"));//

        return img;
    }

    //客户预留信息设置
    public static JSONObject reservedInfo(String reservedInfo){
        JSONObject info = new JSONObject();

        info.put("reservedInfo",reservedInfo);//

        return info;
    }

    //客户信息注销
    public static JSONObject infoLogout(){


        JSONObject logout = publicField;

        logout.put("checkType","0");//  必传  验证方式 0密码 1人脸识别
        logout.put("password","123456");//  条件必传（验证方式未0时必传）
        logout.put("attachments", PublicFunc.attachments());//

        return logout;
    }

    //校验身份信息
    public static JSONObject verifyInfo(JSONObject identityInfo){

        JSONObject info = publicField;

        info.put("certType", identityInfo.getString("certType"));//  必传  证件类型
        info.put("certNo", identityInfo.getString("certNo"));//  证件号码
        info.put("dueDate", identityInfo.getString("dueDate"));//  证件到期日期  datetime

        return info;
    }


}
