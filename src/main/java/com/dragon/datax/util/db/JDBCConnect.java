package com.dragon.datax.util.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName JDBCConnect
 * @Author pengl
 * @Date 2018/11/22 16:38
 * @Description JDBC Connect获取
 * @Version 1.0
 */
public class JDBCConnect {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCConnect.class);
    private String url;
    private String userName;
    private String passWord;
    private String driverClass;
    private Connection conn;

    public JDBCConnect(DataSourceModel dataSourceModel) {
        this.url = dataSourceModel.getDsUrl();
        this.userName = dataSourceModel.getDsUser();
        this.passWord = dataSourceModel.getDsPass();
        try {
            this.driverClass = JdbcUtils.getDriverClassName(dataSourceModel.getDsUrl());
        } catch (SQLException e) {
            this.driverClass = "";
        }
    }

    public Connection getConnection() {
        if(conn == null){
            try {
                Class.forName(driverClass);
                conn = DriverManager.getConnection(url, userName, passWord);
            } catch (ClassNotFoundException e) {
                LOGGER.error("===>>>获取数据源失败：缺少数据库连接驱动...");
                throw new RuntimeException("缺少数据库连接驱动！");
            } catch (SQLException e) {
                LOGGER.error("===>>>获取数据库连接失败：{}", e.getMessage(), e);
                throw new RuntimeException("数据库连接失败！");
            }
        }
        return conn;
    }

    /**
     * 释放连接 Connection
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
