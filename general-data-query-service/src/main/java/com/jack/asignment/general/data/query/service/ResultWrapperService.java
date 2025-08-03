package com.jack.asignment.general.data.query.service;

import com.jack.asignment.general.data.query.dto.SearchRequestDTO;
import com.jack.asignment.general.data.query.dto.SearchResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Util service for wrapper result to accommodate UI
 */
@Service
public class ResultWrapperService {
    public List<String> convertResultToList(List<Map<String, Object>> result) {
        var finalResult = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Map<String, Object> stringObjectMap : result) {
                stringObjectMap.forEach((s, o) -> finalResult.add((String)o));
            }
        }
        return finalResult;
    }

    public SearchResponseDTO convertResult(List<Map<String, Object>> result, SearchRequestDTO searchRequestDTO, Integer totalCount) {
        var response = new SearchResponseDTO();

        if(!CollectionUtils.isEmpty(searchRequestDTO.getSelectedColumnNames())) {
            response.setColumns(searchRequestDTO.getSelectedColumnNames());
        } else {
            if(!CollectionUtils.isEmpty(result)) {
                List<String> cols = new ArrayList<>();
                Map<String, Object> oneLine = result.get(0);
                if(!CollectionUtils.isEmpty(oneLine)) {
                    cols.addAll(oneLine.keySet());
                }
                response.setColumns(cols);
            }
        }
        response.setRecords(result);
        response.setTotalNum(totalCount);
        response.setPageNum(searchRequestDTO.getPageNum());
        response.setPageSize(searchRequestDTO.getPageSize());
        response.setTotalPage((int)Math.ceil((double) totalCount / searchRequestDTO.getPageSize()));
        return response;
    }
}
