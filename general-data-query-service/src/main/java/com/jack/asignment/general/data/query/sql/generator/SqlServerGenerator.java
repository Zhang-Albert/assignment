package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import org.springframework.stereotype.Service;

@Generator("SqlServer")
@Service
public class SqlServerGenerator extends AbstractSqlGenerator{
}
