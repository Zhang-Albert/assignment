package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import com.jack.asignment.general.data.query.dto.SearchRequestDTO;
import org.springframework.stereotype.Service;

@Generator("Oracle")
@Service
public class OracleGenerator extends AbstractSqlGenerator{
    @Override
    public String generateQueryAllTableNames() {
        return "SELECT table_name FROM user_tables ORDER BY table_name";
    }

    @Override
    protected void constructPaginationClause(StringBuilder sb, SearchRequestDTO searchRequestDTO) {
        int offset = (searchRequestDTO.getPageNum() - 1) * searchRequestDTO.getPageSize();
        sb.append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT ").append(searchRequestDTO.getPageSize()).append(" ROWS ONLY ");
    }
}
