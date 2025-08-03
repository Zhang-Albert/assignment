package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import org.springframework.stereotype.Service;

@Generator("H2")
@Service
public class H2Generator extends AbstractSqlGenerator{
}
