package com.jack.asignment.general.data.query.sql.generator;

/**
 * sql generator factory holds all the generators
 */
public interface SqlGeneratorFactory {
    // load generator by type
    SqlGenerator loadSqlGenerator(String type);
}
