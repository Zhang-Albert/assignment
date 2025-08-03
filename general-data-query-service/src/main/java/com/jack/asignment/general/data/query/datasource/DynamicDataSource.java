package com.jack.asignment.general.data.query.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * dynamic datasource route
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    // store all datasource
    private final Map<Object, Object> targetDataSources;

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * key of current datasource
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceKey();
    }

    /**
     * add DS dynamically
     */
    public void addDataSource(String key, DataSource dataSource) {
        targetDataSources.put(key, dataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * check whether the key is mapped to a valid active datasource
     * @param key datasource key
     * @return boolean true if datasource mapped to the key exist and active
     */
    public boolean isDataSourceExists(String key) {
        return targetDataSources.containsKey(key);
    }
}
