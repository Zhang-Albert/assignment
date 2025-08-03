package com.jack.asignment.general.data.query.dto;

/**
 * Database connection info
 */
public class DBConnInfoDTO {
    private String url;
    private String userName;
    private String password;
    // MySQL / h2 / Oracle e.g
    private String type;
    // default pool size
    private int maxPoolSize = 10;
    private int minIdle = 5;
    private String key;
    private String driverClassName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinIdle() {
        return minIdle;
    }


    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMinIdle(int minIdle) {}

    public String getKey() {
        return url+userName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
    public String getDriverClassName() {
        // check type
        if("MySQL".equalsIgnoreCase(type)) {
            driverClassName = "com.mysql.cj.jdbc.Driver";
        } else  if("PostgreSQL".equalsIgnoreCase(type)) {
            driverClassName = "org.postgresql.Driver";
        } else if("Oracle".equalsIgnoreCase(type)) {
            driverClassName = "oracle.jdbc.OracleDriver";
        } else if("SQLServer".equalsIgnoreCase(type)) {
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if("DB2".equalsIgnoreCase(type)) {
            driverClassName = "com.ibm.db2.jcc.DB2Driver";
        } else if("h2".equalsIgnoreCase(type)) {
            driverClassName = "org.h2.Driver";
        } else if("HSQL".equalsIgnoreCase(type)) {
            driverClassName = "org.hsqldb.jdbcDriver";
        }  else if("Informix".equalsIgnoreCase(type)) {
            driverClassName = "org.hibernate.dialect.InnoDBDialect";
        } else if("hive".equalsIgnoreCase(type)) {
            driverClassName = "org.hive.jdbc.DiveDriver";
        }
        return driverClassName;
    }
}
