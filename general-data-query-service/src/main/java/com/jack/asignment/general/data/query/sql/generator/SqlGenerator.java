package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.dto.SearchRequestDTO;

public interface SqlGenerator {
    String generateQueryAllTableNames();
    String generateSelectFor(SearchRequestDTO searchRequestDTO);
    String generateCountSelectFor(SearchRequestDTO searchRequestDTO);
}
