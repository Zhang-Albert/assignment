# Assignment for General Data Query Tool
Front-end implemented by angular.
Back-end implemented by Java Spring / Springboot. Expose 2 RESTful APIs to front-end.

## Prerequsites:
### 1. nodejs
### 2. npm
npm install ng-zorro-antd --save
### 3. Java

##Steps for runing this demo:
### 1. start up front-end application
### 2. start up back-end application
### 3. Access this URL using browser

##System user case:

##System design:

##APIs specifications:
Swagger UI Link:
http://localhost:8080/swagger-ui/index.html#/

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
POST	| /general/data/query/datasource	| Register new DataSource and Load all tables.	          |  | 	
POST	| /general/data/query/tables/{tableName}/records	| Search table records by criteria.	| × | × 
