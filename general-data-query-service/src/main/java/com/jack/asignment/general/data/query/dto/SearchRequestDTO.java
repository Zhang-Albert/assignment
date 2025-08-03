package com.jack.asignment.general.data.query.dto;

import java.util.List;
import java.util.Map;

/**
 * search request params
 */
public class SearchRequestDTO {
    private DBConnInfoDTO dbConnInfoDTO;
    private int pageSize;
    private int pageNum;
    private String tableName;
    private Map<String,String> selectedColumnCriteriaValue;
    private Map<String,String> selectedColumnOperation;
    private List<String> selectedColumnNames;
    private String orderBy;
    private String order;
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

    public Map<String, String> getSelectedColumnCriteriaValue() {
        return selectedColumnCriteriaValue;
    }

    public void setSelectedColumnCriteriaValue(Map<String, String> selectedColumnCriteriaValue) {
        this.selectedColumnCriteriaValue = selectedColumnCriteriaValue;
    }

    public Map<String, String> getSelectedColumnOperation() {
        return selectedColumnOperation;
    }

    public void setSelectedColumnOperation(Map<String, String> selectedColumnOperation) {
        this.selectedColumnOperation = selectedColumnOperation;
    }

    public List<String> getSelectedColumnNames() {
        return selectedColumnNames;
    }

    public void setSelectedColumnNames(List<String> selectedColumnNames) {
        this.selectedColumnNames = selectedColumnNames;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
