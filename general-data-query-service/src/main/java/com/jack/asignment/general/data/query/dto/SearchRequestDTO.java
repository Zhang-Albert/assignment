package com.jack.asignment.general.data.query.dto;

/**
 * search request params
 */
public class SearchRequestDTO {
    private DBConnInfoDTO dbConnInfoDTO;
    private int pageSize;
    private int pageNum;
    private String tableName;

    public DBConnInfoDTO getDbConnInfoDTO() {
        return dbConnInfoDTO;
    }

    public void setDbConnInfoDTO(DBConnInfoDTO dbConnInfoDTO) {
        this.dbConnInfoDTO = dbConnInfoDTO;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
