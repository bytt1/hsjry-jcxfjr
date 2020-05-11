package utils;

import com.alibaba.fastjson.JSONObject;
import data.publicdata.PublicFunc;
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
    private static final String DATABASE = PublicFunc.getDatabase();
    private static Connection connection;
    private static ResultSet r;

    private static Connection con(String database) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        log.info("MYSQL地址 >> " + getDataBaseUrl(database));
        connection = DriverManager.getConnection(getDataBaseUrl(database),userName,pwd);
        return connection;
    }

    public static ResultSet selectAll(String sql) throws SQLException, ClassNotFoundException {
        Statement statement = con(DATABASE).createStatement();
        r = statement.executeQuery(sql);

        return r;
    }

    public static String select(String sql) throws SQLException, ClassNotFoundException {
        Statement statement = con(DATABASE).createStatement();
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
