package com.jack.asignment.general.data.query.datasource;


/**
 * Datasource context holder, used for switching Datasource.
 */
public class DataSourceContextHolder {
    // ThreadLocal store the key current DS
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * set DS key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * get DS key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * clear DS key
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }
}
