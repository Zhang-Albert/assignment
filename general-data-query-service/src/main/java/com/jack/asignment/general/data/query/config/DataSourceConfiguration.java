package com.jack.asignment.general.data.query.config;
import com.jack.asignment.general.data.query.datasource.DataSourceFactory;
import com.jack.asignment.general.data.query.datasource.DynamicDataSource;
import com.jack.asignment.general.data.query.dto.DBConnInfoDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Data source auto config
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * default DS
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DBConnInfoDTO defaultDataSourceConfig() {
        return new DBConnInfoDTO();
    }

    /**
     * create dynamic DS
     */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        // default one
        var defaultOne = defaultDataSourceConfig();
        defaultOne.setDriverClassName(defaultOne.getDriverClassName());
        defaultOne.setKey(defaultOne.getKey());
        DataSource defaultDataSource = DataSourceFactory.createDataSource(defaultOne);

        // add default one into dynamic ds
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(defaultDataSourceConfig().getKey(), defaultDataSource);

        return new DynamicDataSource(defaultDataSource, dataSources);
    }

    /**
     * Transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
