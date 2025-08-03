package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import com.jack.asignment.general.data.query.dto.SearchRequestDTO;
import org.springframework.stereotype.Service;

@Generator("Db2")
@Service
public class Db2Generator extends AbstractSqlGenerator{
    @Override
    public String generateQueryAllTableNames() {
        return "SELECT TABNAME FROM SYSCAT.TABLES WHERE TABSCHEMA = CURRENT_USER AND TYPE = 'T' ORDER BY TABNAME";
    }
    @Override
    protected void constructPaginationClause(StringBuilder sb, SearchRequestDTO searchRequestDTO) {
        int offset = (searchRequestDTO.getPageNum() - 1) * searchRequestDTO.getPageSize();
        sb.append(" OFFSET ").append(offset).append(" ROWS FETCH FIRST ").append(searchRequestDTO.getPageSize()).append(" ROWS ONLY ");
    }
}
