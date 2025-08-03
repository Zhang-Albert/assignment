package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.dto.SearchRequestDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * default behavior here
 */
public abstract class AbstractSqlGenerator implements SqlGenerator {
    @Override
    public String generateSelectFor(SearchRequestDTO searchRequestDTO) {
        StringBuilder sql = new StringBuilder();
        constructSelectColumns(sql,searchRequestDTO);
        constructFromClause(sql,searchRequestDTO);
        constructWhereClause(sql,searchRequestDTO);
        constructOrderByClause(sql,searchRequestDTO);
        constructPaginationClause(sql,searchRequestDTO);
        return sql.toString();
    }

    /**
     * Need to select count for pagination
     * @param searchRequestDTO request params
     * @return SQL for select count based on given criteria
     */
    @Override
    public String generateCountSelectFor(SearchRequestDTO searchRequestDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("Select count(1) ");
        constructFromClause(sql,searchRequestDTO);
        constructWhereClause(sql,searchRequestDTO);
        // constructOrderByClause(sql,searchRequestDTO);
        // constructPaginationClause(sql,searchRequestDTO);
        return sql.toString();
    }

    @Override
    public String generateQueryAllTableNames() {
        return "show tables";
    }

    protected void constructSelectColumns(StringBuilder sb,SearchRequestDTO searchRequestDTO) {
        if(!CollectionUtils.isEmpty(searchRequestDTO.getSelectedColumnNames())) {
            StringJoiner stringJoiner = new StringJoiner(",");
            searchRequestDTO.getSelectedColumnNames().forEach(stringJoiner::add);
            sb.append("select ").append(stringJoiner);
        } else {
            sb.append("Select * ");
        }
    }

    protected void constructFromClause(StringBuilder sb,SearchRequestDTO searchRequestDTO) {
        sb.append(" from ").append(searchRequestDTO.getTableName());
    }

    protected void constructWhereClause(StringBuilder sb,SearchRequestDTO searchRequestDTO) {
        if(!CollectionUtils.isEmpty(searchRequestDTO.getSelectedColumnOperation())
                && !CollectionUtils.isEmpty(searchRequestDTO.getSelectedColumnCriteriaValue())) {
            sb.append(" where ");
            List<Map.Entry<String,String>> entryList = searchRequestDTO.getSelectedColumnOperation().entrySet().stream().toList();
            for (int i = 0; i < entryList.size(); i++) {
                String item = entryList.get(i).getKey();
                String value = searchRequestDTO.getSelectedColumnCriteriaValue().get(item);
                String operation = searchRequestDTO.getSelectedColumnOperation().get(item);
                if(operation != null && !operation.equalsIgnoreCase("") &&
                        value != null && !value.equalsIgnoreCase("")) {
                    value = value.trim();
                    sb.append(item).append(" ");
                    if("like".equalsIgnoreCase(operation)){
                        sb.append(operation).append(" '%").append(value).append("%' ");
                    } else if("between".equalsIgnoreCase(operation)){
                        String[] values = value.split("#");
                        sb.append(operation)
                                .append(" ").append(convertValue(values[0])).append(" and ").append(convertValue(values[1]));
                    } else {
                        sb.append(operation)
                                .append(" ").append(convertValue(value));
                    }
                    if(i != searchRequestDTO.getSelectedColumnOperation().size()-1) {
                        sb.append(" and ");
                    } else {
                        sb.append(" ");
                    }
                }
            }
        }
    }

    private String convertValue(String input) {
        if(!isNumber(input)) {
            return "'" + input.trim() + "'";
        } else {
            return input;
        }
    }

    public static boolean isNumber(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected void constructOrderByClause(StringBuilder sb,SearchRequestDTO searchRequestDTO) {

    }

    protected void constructPaginationClause(StringBuilder sb,SearchRequestDTO searchRequestDTO) {
        int offset = (searchRequestDTO.getPageNum() - 1) * searchRequestDTO.getPageSize();
        sb.append(" limit ").append(searchRequestDTO.getPageSize()).append(" offset ").append(offset);
    }
}
