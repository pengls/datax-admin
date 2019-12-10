package com.dragon.datax.util.db;

import com.dragon.boot.web.exception.BusErrorException;
import com.dragon.datax.model.Dts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName JdbcConnect
 * @Author pengl
 * @Date 2018/11/22 16:38
 * @Description JDBC Connect获取
 * @Version 1.0
 */
@Slf4j
@Data
public class JdbcConnect {
    private String url;
    private String userName;
    private String passWord;
    private String driverClass;
    private Connection conn;

    public JdbcConnect(Dts dts) {
        this.url = dts.getJdbcUrl();
        this.userName = dts.getUser();
        this.passWord = dts.getPass();
        try {
            this.driverClass = DriverUtil.getDriverClassName(dts.getJdbcUrl());
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
                log.error("===>>>获取数据库连接失败：缺少数据库连接驱动！");
                throw new BusErrorException("缺少数据库连接驱动！");
            } catch (SQLException e) {
                log.error("===>>>获取数据库连接失败：{}", e.getMessage(), e);
                throw new BusErrorException("数据库连接失败：" + e.getMessage());
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

}
