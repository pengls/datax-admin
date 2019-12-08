package com.dragon.datax.util.db;

import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName DBHandle
 * @Author pengl
 * @Date 2018/11/24 11:40
 * @Description TODO
 * @Version 1.0
 */
public class DBHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBHandle.class);
    /**
     * 获取所有的表
     *
     * @return
     */
    public static List<String> getTables(Connection conn, String schema) throws SQLException {
        List<String> res = new ArrayList<>();
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            String catalog = conn.getCatalog();
            String userName = metaData.getUserName();
            String productName = metaData.getDatabaseProductName();
            if(StringUtils.isNotBlank(schema)){
                userName = schema;
            }
            LOGGER.info("===>>>Catalog: {}, username: {}, productname: {}", catalog, userName, productName);
            ResultSet rs = metaData.getTables(catalog,
                    convertDatabaseSchameType(userName, productName),
                    null,
                    new String[] { "TABLE" , "VIEW"});
            while(rs.next()) {
                res.add(rs.getString("TABLE_NAME"));
            }
        }finally {
            DbUtils.close(conn);
        }
        return res;
    }

    /**
     * 获取所有的列
     *
     * @return
     */
    public static List<String> getColums(Connection conn, String schema, String tableNme) throws SQLException {
        List<String> res = new ArrayList<>();
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            String catalog = conn.getCatalog();
            String userName = metaData.getUserName();
            if(StringUtils.isNotBlank(schema)){
                userName = schema;
            }
            String productName = metaData.getDatabaseProductName();
            ResultSet rs = metaData.getColumns(catalog,
                    convertDatabaseSchameType(userName, productName),
                    tableNme,
                    null);
            while(rs.next()) {
                res.add(rs.getString("COLUMN_NAME"));
            }
        }finally {
            DbUtils.close(conn);
        }
        return res;
    }
    /**
     * 获取数据库名称
     * @return
     */
    public static String getDbName(Connection conn) throws SQLException {
        try{
            return conn.getCatalog();
        }finally {
            DbUtils.close(conn);
        }
    }

    private static String convertDatabaseSchameType(String user, String type) {
        type = type.toUpperCase(Locale.ENGLISH);
        switch (type){
            case "ORACLE":
                user = user.toUpperCase(Locale.ENGLISH);
                break;
            case "DB2":
                user = user.toUpperCase(Locale.ENGLISH);
                break;
            case "MICROSOFT SQL SERVER":
                user = "dbo";
                break;
            default:
                user = null;
                break;

        }
        LOGGER.info("===>>>SchameType转换结果：{}", user);
        return user;
    }

    @Deprecated
    private static String convertDatabaseCatalogType(String catalog, String type) {
        type = type.toUpperCase(Locale.ENGLISH);
        switch (type){
            case "ORACLE":
                catalog = "null";
                break;
            default:
                break;

        }
        LOGGER.info("===>>>CatalogType转换结果：{}", catalog);
        return catalog;
    }

    public static void main(String[] args) throws SQLException {
        DataSourceModel dsm = new DataSourceModel();
        dsm.setDsUser("U_STD_YQCX");
        dsm.setDsPass("UynB6Cubc9Rg");
        dsm.setDsUrl("jdbc:oracle:thin:@//10.0.8.13:1522/zzyqdb");
        JDBCConnect jdbcConnect = new JDBCConnect(dsm);
        Connection conn = jdbcConnect.getConnection();
        //List<String> list = DBHandle.getTables(conn, "U_STD_APP");
        List<String> list = DBHandle.getColums(conn, "U_STD_APP", "YQ_PS_WORK_HOUR_TMP");
        System.out.println("===>>>" + JSON.toJSONString(list));
    }
}
