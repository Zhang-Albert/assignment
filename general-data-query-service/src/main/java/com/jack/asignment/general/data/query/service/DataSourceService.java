package com.jack.asignment.general.data.query.service;

import com.jack.asignment.general.data.query.datasource.DataSourceContextHolder;
import com.jack.asignment.general.data.query.datasource.DataSourceFactory;
import com.jack.asignment.general.data.query.datasource.DynamicDataSource;
import com.jack.asignment.general.data.query.dto.DBConnInfoDTO;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Datasource service for e.g. registering new DS / switch DS
 */
@Service
public class DataSourceService {

    final private DynamicDataSource dynamicDataSource;

    public DataSourceService(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    /**
     * register new DS
     */
    public void registerDataSource(DBConnInfoDTO config) {
        DataSource dataSource = DataSourceFactory.createDataSource(config);
        dynamicDataSource.addDataSource(config.getKey(), dataSource);
    }

    /**
     * switch DS
     */
    public void switchDataSource(String key) {
        DataSourceContextHolder.setDataSourceKey(key);
        // dynamicDataSource.afterPropertiesSet();
    }

    /**
     * clear datasource
     */
    public void clearDataSource() {
        DataSourceContextHolder.clearDataSourceKey();
    }

    /**
     * check whether the key is mapped to a valid active datasource
     * @param key datasource key
     * @return boolean true if datasource mapped to the key exist and active
     */
    public boolean isDataSourceExist(String key) {
        return dynamicDataSource.isDataSourceExists(key);
    }
}