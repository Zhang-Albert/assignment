package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import org.springframework.stereotype.Service;

@Generator("PostgreSQL")
@Service
public class PostgreGenerator extends AbstractSqlGenerator{

    @Override
    public String generateQueryAllTableNames() {
        return "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema') AND table_type = 'BASE TABLE' ORDER BY table_name";
    }
}