package com.jack.asignment.general.data.query.controller;

import com.jack.asignment.general.data.query.dto.DBConnInfoDTO;
import com.jack.asignment.general.data.query.dto.SearchRequestDTO;
import com.jack.asignment.general.data.query.dto.SearchResponseDTO;
import com.jack.asignment.general.data.query.service.DataSourceService;
import com.jack.asignment.general.data.query.service.ResultWrapperService;
import com.jack.asignment.general.data.query.sql.generator.SqlGeneratorFactory;
import com.jack.asignment.general.data.query.util.RetCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value =  "/general/data/query/")
@CrossOrigin(origins = "*")
public class GeneralDataQueryController {
    private final DataSourceService dataSourceService;
    private final JdbcTemplate jdbcTemplate;
    private final SqlGeneratorFactory sqlGeneratorFactory;
    private final ResultWrapperService resultWrapperService;
    public GeneralDataQueryController(DataSourceService dataSourceService,
                                      JdbcTemplate jdbcTemplate,
                                      SqlGeneratorFactory sqlGeneratorFactory,
                                      ResultWrapperService resultWrapperService) {
        this.dataSourceService = dataSourceService;
        this.jdbcTemplate = jdbcTemplate;
        this.sqlGeneratorFactory = sqlGeneratorFactory;
        this.resultWrapperService = resultWrapperService;
    }

    /**
     * Create Data Source based on user input DB connection info. If success return all the table names under this Database.
     * @param dbConnInfoDTO database connection info
     * @return table name list under specified DB
     */
    @PostMapping(value = "/datasource")
    public RetCode<List<String>> createDataSource(@RequestBody DBConnInfoDTO dbConnInfoDTO) {
        List<Map<String, Object>> result;
        try{
            // 0. is this info already created?
            if (!dataSourceService.isDataSourceExist(dbConnInfoDTO.getKey())) {
                // 1. register new Datasource
                dataSourceService.registerDataSource(dbConnInfoDTO);
            }
            // 2 switch datasource based on the key
            dataSourceService.switchDataSource(dbConnInfoDTO.getKey());
            // 3. generate SQL for select all tables
            String sql = sqlGeneratorFactory.loadSqlGenerator(dbConnInfoDTO.getType()).generateQueryAllTableNames();
            // 4. execute SQL
            result = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            return new RetCode<>("500103",e.getMessage());
        } finally {
            dataSourceService.clearDataSource();
        }
        // 5. return all table names to UI
        return RetCode.success(resultWrapperService.convertResultToList(result));
    }

    /**
     * Search table records, based on criteria passed from UI.
     * @param searchRequestDTO wrapper request parameter
     * @return SearchResponseDTO
     */
    @PostMapping(value = "/tables/records")
    @CrossOrigin(origins = "*")
    public RetCode<SearchResponseDTO> fetchRecords(@RequestBody SearchRequestDTO searchRequestDTO) {
        List<Map<String, Object>> result;
        Integer totalCount = 0;
        try{
            // 0. is DS already created?
            if (!dataSourceService.isDataSourceExist(searchRequestDTO.getDbConnInfoDTO().getKey())) {
                return new RetCode<>("500501", "DB connection is not exist / lost, Please provide DB connection info and try to click Connect button.");
            }
            // 1. switch datasource
            dataSourceService.switchDataSource(searchRequestDTO.getDbConnInfoDTO().getKey());
            // 2. generate select SQL
            var generator = sqlGeneratorFactory.loadSqlGenerator(searchRequestDTO.getDbConnInfoDTO().getType());
            String sql = generator.generateSelectFor(searchRequestDTO);
            String sqlCount = generator.generateCountSelectFor(searchRequestDTO);
            // 3. execute SQL
            result = jdbcTemplate.queryForList(sql);
            System.out.println(sql);
            System.out.println(sqlCount);
            // 3.1 count
            if(!CollectionUtils.isEmpty(result)){
                totalCount = jdbcTemplate.queryForObject(sqlCount,Integer.class);
            }
        } catch (Exception e){
            return new RetCode<>("500102",e.getMessage());
        } finally {
            dataSourceService.clearDataSource();
        }
        // 4. return dynamic UI grid data
        return RetCode.success(resultWrapperService.convertResult(result,searchRequestDTO,totalCount));
    }
}