import {ChangeDetectorRef, Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {ApiService, DbConnInfo, GridData, SearchRequest} from './api.service';
import {catchError, finalize, of} from 'rxjs';
import * as e from 'express';

@Component({
  selector: 'app-root',
  imports: [FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  constructor(private apiService:ApiService, private cdr: ChangeDetectorRef) {}

  protected title = 'General-Data-Query-UI';

  supportedDB = [
    "MySQL",
    "H2",
    "Oracle",
    "DB2",
    "MsSQL",
    "PostgreSQL",
    "Hive",
    "ClickHouse"
  ];
  operationList: String[] = [
    "Select operator",
    ">",
    ">=",
    "<",
    "<=",
    "=",
    "Like",
    "Between"
  ]
  allTables:String[] = [
    "No data",
  ];
  gridData: GridData = {
    columns: [],
    records: [],
    pageNum: 1,
    pageSize: 10,
    totalPage: 0,
    totalNum: 0,
  }
  selectedDBType: String = "MySQL";
  url: String = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
  userName: String = "";
  password: String = "";
  selectedDBTable:String ="No data";
  isLoading: boolean = false;
  loadingMessage: string = '';
  errorMessage = '';
  serviceResult: any;
  serviceGridResult: any;

  connect(): void {
    this.isLoading = true;
    this.loadingMessage = 'Loading...';
    this.clearSelected();
    const dbConnInfo: DbConnInfo = {
      userName: this.userName.trim(),
      url: this.url.trim(),
      password: this.password.trim(),
      type: this.selectedDBType.trim(),
    };
    this.apiService.registerDataBaseAndGetTableNames(dbConnInfo).pipe(
      catchError(error => {
        this.refreshElement();
        this.errorMessage = error;
        console.error('API failure:', error);
        return of([]);
      }),
      finalize(()=> {
        this.isLoading = false;
        this.loadingMessage = '';
        this.refreshElement();
      })
    ).subscribe(data => {
      // console.log(data);
      this.serviceResult = data;
      if (this.serviceResult.retCode != '200') {
        alert("Fail: "+this.serviceResult.msg);
        this.isLoading = false;
        this.loadingMessage = '';
      } else {
        this.allTables = [...this.serviceResult.result];
        // console.log(this.allTables)
        this.selectedDBTable = this.allTables[0];
        // populate grid data
        this.searchTableData();
      }
      this.refreshElement();
    });
  }
  refreshElement(){
    this.cdr.detectChanges();
    this.cdr.markForCheck();
  }
  clearTableNameDropdown() {
    this.allTables = ['No data'];
    this.selectedDBTable = 'No data';
    this.cdr.detectChanges();
    this.cdr.markForCheck();
  }

  clearSelected() {
    this.selectedColumns = [];
    this.selectedColumnOperation = new Map<String, String>();
    this.selectedColumnCriteriaValue = new Map<String, String>();
    this.gridData.columns = [];
    this.gridData.records = [];
    this.refreshElement();
  }

  searchTableData() {
    if(this.selectedDBTable === '' || this.selectedDBTable === 'No data') {
      alert("Please connect to database and select a table to proceed.");
      return;
    }
    if(this.url === '') {
      alert("Please provide URL to connect to database and select a table to proceed.");
      return;
    }
    if(this.userName === '') {
      alert("Please provide user name to connect to database and select a table to proceed.");
      return;
    }
    if(this.password === '') {
      alert("Please provide password to connect to database and select a table to proceed.");
      return;
    }
    if(this.selectedDBType === '') {
      alert("Please provide database type to connect to database and select a table to proceed.");
      return;
    }
    this.isLoading = true;
    this.loadingMessage = 'Loading...';
    const dbConnInfo: DbConnInfo = {
      userName: this.userName.trim(),
      url: this.url.trim(),
      password: this.password.trim(),
      type: this.selectedDBType.trim(),
    };
    const searchRequest: SearchRequest = {
      dbConnInfoDTO: dbConnInfo,
      pageNum: this.pageNum,
      pageSize: this.pageSize,
      tableName: this.selectedDBTable,
      selectedColumnCriteriaValue: Object.fromEntries(this.selectedColumnCriteriaValue),
      selectedColumnOperation: Object.fromEntries(this.selectedColumnOperation),
      selectedColumnNames: this.selectedColumns,
    };
    this.apiService.searchDataForTable(searchRequest).pipe(
      catchError(error => {
        this.refreshElement();
        this.errorMessage = error;
        console.error('API failure:', error);
        return of([]);
      }),
      finalize(()=> {
        this.isLoading = false;
        this.loadingMessage = '';
        this.refreshElement();
      })
    ).subscribe(data => {
      this.serviceGridResult = data;
      // console.log(this.serviceGridResult)
      if (this.serviceGridResult.retCode != '200') {
        alert("Fail: "+this.serviceGridResult.msg);
        this.isLoading = false;
        this.loadingMessage = '';
      } else {
        this.gridData = this.serviceGridResult.result;
        this.gridData.records = this.gridData.records.map(item => {
          return new Map(Object.entries(item));
        });
        // console.log(this.gridData)
        this.pageNum = this.gridData.pageNum;
        this.totalItems = this.gridData.totalNum;
        this.totalPages = this.gridData.totalPage;
        this.selectedColumns = this.gridData.columns;
      }
      this.refreshElement();
    });
  }
  pageNum = 1;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;

  onPageChange(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.pageNum = page;
    this.searchTableData();
  }

  onPageSizeChange(size: number): void {
    this.pageSize = size;
    this.pageNum = 1;
    this.searchTableData();
  }
  selectedColumns: String[] = [];
  updateSelectedColumn(event: Event, col:String) {
    const input = event.target as HTMLInputElement;
    const isChecked = input.checked;
    if(isChecked) {
      this.selectedColumns.push(col);
      //
    } else {
      this.selectedColumns = this.selectedColumns.filter(colName => colName !== col);
      this.selectedColumnOperation.delete(col);
      this.selectedColumnCriteriaValue.delete(col);
      //alert("remove");
    }
    //alert(this.selectedColumns)
  }

  selectedColumnOperation: Map<String,String> = new Map<String,String>();
  updateOperationChange(event: Event, col:String) {
    if(!this.selectedColumns.includes(col)) {
      return;
    }
    const input = event.target as HTMLSelectElement;
    const operation = input.value;
    // alert(col+":"+operation);
    if(operation != '' && operation != 'Select operator') {
      this.selectedColumnOperation.set(col,operation);
    } else {
      this.selectedColumnOperation.delete(col);
    }
    /*for (const [key, value] of this.selectedColumnOperation) {
      console.log(`key: ${key}, value: ${value}`);
    }*/
  }

  selectedColumnCriteriaValue: Map<String,String> =  new Map<String,String>();
  updateSelectedColumnCriteriaValue(event: Event, col:String) {
    if(!this.selectedColumns.includes(col)) {
      return;
    }
    const input = event.target as HTMLInputElement;
    const value = input.value;
    // alert(col+":"+value);
    if(value != '') {
      this.selectedColumnCriteriaValue.set(col,value);
    }
  }
  protected readonly Object = Object;
}
