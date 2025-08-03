package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.dto.SearchRequestDTO;

/**
 * default behavior here
 */
public abstract class AbstractSqlGenerator implements SqlGenerator {
    @Override
    public String generateSelectFor(SearchRequestDTO searchRequestDTO) {
        int offset = (searchRequestDTO.getPageNum() - 1) * searchRequestDTO.getPageSize();
        return "select * from " + searchRequestDTO.getTableName() + " limit "+ searchRequestDTO.getPageSize()+ " offset " + offset;
    }

    /**
     * Need to select count for pagination
     * @param searchRequestDTO request params
     * @return SQL for select count based on given criteria
     */
    @Override
    public String generateCountSelectFor(SearchRequestDTO searchRequestDTO) {
        return "select count(1) from " + searchRequestDTO.getTableName();
    }

    @Override
    public String generateQueryAllTableNames() {
        return "show tables";
    }
}
