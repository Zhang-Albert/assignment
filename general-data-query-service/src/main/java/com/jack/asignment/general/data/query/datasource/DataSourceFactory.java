package com.jack.asignment.general.data.query.datasource;

import com.jack.asignment.general.data.query.dto.DBConnInfoDTO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

/**
 * DS factory
 */
public class DataSourceFactory {

    /**
     * create datasource based on db connection info
     */
    public static DataSource createDataSource(DBConnInfoDTO config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUserName());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());
        hikariConfig.setMinimumIdle(config.getMinIdle());
        // could create more
        return new HikariDataSource(hikariConfig);
    }
}

