package com.dragon.datax.util.db;

import java.sql.SQLException;

/**
 * @ClassName DriverUtil
 * @Author pengl
 * @Date 2019-12-09 14:10
 * @Description TODO
 * @Version 1.0
 */
public class DriverUtil implements JdbcConstants {
    private static Boolean mysql_driver_version_6 = null;


    public static String getDriverClassName(String rawUrl) throws SQLException {
        if (rawUrl == null) {
            return null;
        }

        if (rawUrl.startsWith("jdbc:derby:")) {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        } else if (rawUrl.startsWith("jdbc:mysql:")) {
            if (mysql_driver_version_6 == null) {
                mysql_driver_version_6 = loadClass("com.mysql.cj.jdbc.Driver") != null;
            }

            if (mysql_driver_version_6) {
                return MYSQL_DRIVER_6;
            } else {
                return MYSQL_DRIVER;
            }
        } else if (rawUrl.startsWith("jdbc:log4jdbc:")) {
            return LOG4JDBC_DRIVER;
        } else if (rawUrl.startsWith("jdbc:mariadb:")) {
            return MARIADB_DRIVER;
        } else if (rawUrl.startsWith("jdbc:oracle:") //
                || rawUrl.startsWith("JDBC:oracle:")) {
            return ORACLE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:alibaba:oracle:")) {
            return ALI_ORACLE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:microsoft:")) {
            return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (rawUrl.startsWith("jdbc:sqlserver:")) {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if (rawUrl.startsWith("jdbc:sybase:Tds:")) {
            return "com.sybase.jdbc2.jdbc.SybDriver";
        } else if (rawUrl.startsWith("jdbc:jtds:")) {
            return "net.sourceforge.jtds.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:fake:") || rawUrl.startsWith("jdbc:mock:")) {
            return "com.alibaba.druid.mock.MockDriver";
        } else if (rawUrl.startsWith("jdbc:postgresql:")) {
            return POSTGRESQL_DRIVER;
        } else if (rawUrl.startsWith("jdbc:edb:")) {
            return ENTERPRISEDB_DRIVER;
        } else if (rawUrl.startsWith("jdbc:odps:")) {
            return ODPS_DRIVER;
        } else if (rawUrl.startsWith("jdbc:hsqldb:")) {
            return "org.hsqldb.jdbcDriver";
        } else if (rawUrl.startsWith("jdbc:db2:")) {
            return DB2_DRIVER;
        } else if (rawUrl.startsWith("jdbc:sqlite:")) {
            return SQLITE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:ingres:")) {
            return "com.ingres.jdbc.IngresDriver";
        } else if (rawUrl.startsWith("jdbc:h2:")) {
            return H2_DRIVER;
        } else if (rawUrl.startsWith("jdbc:mckoi:")) {
            return "com.mckoi.JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:cloudscape:")) {
            return "COM.cloudscape.core.JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:informix-sqli:")) {
            return "com.informix.jdbc.IfxDriver";
        } else if (rawUrl.startsWith("jdbc:timesten:")) {
            return "com.timesten.jdbc.TimesTenDriver";
        } else if (rawUrl.startsWith("jdbc:as400:")) {
            return "com.ibm.as400.access.AS400JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:sapdb:")) {
            return "com.sap.dbtech.jdbc.DriverSapDB";
        } else if (rawUrl.startsWith("jdbc:JSQLConnect:")) {
            return "com.jnetdirect.jsql.JSQLDriver";
        } else if (rawUrl.startsWith("jdbc:JTurbo:")) {
            return "com.newatlanta.jturbo.driver.Driver";
        } else if (rawUrl.startsWith("jdbc:firebirdsql:")) {
            return "org.firebirdsql.jdbc.FBDriver";
        } else if (rawUrl.startsWith("jdbc:interbase:")) {
            return "interbase.interclient.Driver";
        } else if (rawUrl.startsWith("jdbc:pointbase:")) {
            return "com.pointbase.jdbc.jdbcUniversalDriver";
        } else if (rawUrl.startsWith("jdbc:edbc:")) {
            return "ca.edbc.jdbc.EdbcDriver";
        } else if (rawUrl.startsWith("jdbc:mimer:multi1:")) {
            return "com.mimer.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:dm:")) {
            return JdbcConstants.DM_DRIVER;
        } else if (rawUrl.startsWith("jdbc:kingbase:")) {
            return JdbcConstants.KINGBASE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:hive:")) {
            return JdbcConstants.HIVE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:hive2:")) {
            return JdbcConstants.HIVE_DRIVER;
        } else if (rawUrl.startsWith("jdbc:phoenix:thin:")) {
            return "org.apache.phoenix.queryserver.client.Driver";
        } else if (rawUrl.startsWith("jdbc:phoenix://")) {
            return JdbcConstants.PHOENIX_DRIVER;
        } else if (rawUrl.startsWith("jdbc:kylin:")) {
            return JdbcConstants.KYLIN_DRIVER;
        } else {
            throw new SQLException("unkow jdbc driver : " + rawUrl);
        }
    }

    private static Class<?> loadClass(String className) {
        Class<?> clazz = null;

        if (className == null) {
            return null;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // skip
        }

        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            try {
                clazz = ctxClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                // skip
            }
        }

        return clazz;
    }
}
