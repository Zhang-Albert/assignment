package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import org.springframework.stereotype.Service;

@Generator("Oracle")
@Service
public class OracleGenerator extends AbstractSqlGenerator{
}
