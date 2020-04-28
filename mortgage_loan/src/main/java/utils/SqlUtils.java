package utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUtils {

    private static final Logger log = LoggerFactory.getLogger(SqlUtils.class);
    private static String userName = Okhttp.getPropertiesVal("userName");
    private static String pwd = Okhttp.getPropertiesVal("passWorld");
    private static String driver = Okhttp.getPropertiesVal("driverPath");
    private static Connection connection;
    private static ResultSet r;

    private static Connection con(String database) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        log.info("MYSQL地址 >> " + getDataBaseUrl(database));
        connection = DriverManager.getConnection(getDataBaseUrl(database),userName,pwd);
        return connection;
    }

    public static ResultSet selectAll(String sql,String database) throws SQLException, ClassNotFoundException {
        Statement statement = con(database).createStatement();
        r = statement.executeQuery(sql);

        return r;
    }

    public static String select(String sql,String database) throws SQLException, ClassNotFoundException {
        Statement statement = con(database).createStatement();
        r = statement.executeQuery(sql);
        while (r.next()){
            return r.getString(1);
        }
        return null;
    }

    public static int upDate(String sql,String database) throws SQLException, ClassNotFoundException {
        Statement statement = con(database).createStatement();
        return statement.executeUpdate(sql);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String sql = "SELECT\n" +
                "\tcertificate_no,\n" +
                "\tuser_name,\n" +
                "\tcreate_time,\n" +
                "\tupdate_time\n" +
                "FROM\n" +
                "\tcredit_affiliated_info\n" +
                "WHERE\n" +
                "\tmain_borrower_id = (\n" +
                "\t\tSELECT\n" +
                "\t\t\tuser_id\n" +
                "\t\tFROM\n" +
                "\t\t\tdev_user.user_contact_station_info\n" +
                "\t\tWHERE\n" +
                "\t\t\tstation_id = (\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\tstation_id\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tdev_user.user_telephone_info\n" +
                "\t\t\t\tWHERE\n" +
                "\t\t\t\t\ttelephone = \"15778841898\"\n" +
                "\t\t\t\tLIMIT 1\n" +
                "\t\t\t)\n" +
                "\t)\n" +
                "ORDER BY\n" +
                "\tupdate_time DESC ";

        ResultSet result = SqlUtils.selectAll(sql,"dev_credit");
        List<Object> list = new ArrayList<>();

        while (result.next()){
            JSONObject corrInfo = new JSONObject();
            corrInfo.put("userName",result.getString("user_name"));
            corrInfo.put("certNo",result.getString("certificate_no"));
            list.add(corrInfo);
        }
        for (Object o : list){
            System.out.println(o);
        }
    }

    private static String getDataBaseUrl(String database){
        return Okhttp.getPropertiesVal("mySql-sit") + database + "?serverTimezone=UTC";
    }

    public static void closeConnection(){
        try {
            r.close();
            connection.close();
            log.info("关闭数据库链接 >>>> ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
