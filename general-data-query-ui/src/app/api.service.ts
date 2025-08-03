import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: "root"
})
export class ApiService {
  private apiUrlRegisterDataSource = 'http://localhost:8080/general/data/query/datasource';
  private apiUrlSearchData = 'http://localhost:8080/general/data/query/tables/records';

  constructor(private http: HttpClient) { }

  // register DS and load all DB tables
  registerDataBaseAndGetTableNames(dbInfo:DbConnInfo): Observable<RetCode<String[]>> {
    return this.http.post<RetCode<String[]>>(this.apiUrlRegisterDataSource,dbInfo);
  }

  // query data records
  searchDataForTable(searchRequest:SearchRequest): Observable<RetCode<GridData>> {
    return this.http.post<RetCode<GridData>>(this.apiUrlSearchData,searchRequest);
  }
}

export interface DbConnInfo {
  type: String;
  userName: String;
  password: String;
  url: String;
}

export interface GridData {
  columns: String[],
  records: Map<String, Object> [],
  pageNum: number,
  pageSize: number,
  totalNum: number,
  totalPage: number,
}
export interface SearchRequest {
  dbConnInfoDTO:DbConnInfo,
  selectedColumnNames: String[],
  selectedColumnOperation: Map<String,String>,
  selectedColumnCriteriaValue: Map<String,String>,
  pageSize: number,
  pageNum: number,
  tableName: String,
  orderBy: String,
  order: String
}

export interface RetCode<T> {
  msg: String;
  retCode: String;
  result: T;
}
