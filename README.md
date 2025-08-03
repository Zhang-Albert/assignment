# Assignment for General Data Query Tool
Front-end implemented by angular.
Back-end implemented by Java Spring / Springboot. Expose 2 RESTful APIs to front-end.

## Prerequsites:
### 1. install nodejs
### 2. install npm
### 3. install angular 
### 4. install JDK 17+

## Steps for runing this demo:
### 1. start up front-end application
#### npm install
#### npm run start
### 2. start up back-end application
java -jar query-0.0.1-SNAPSHOT.jar
### 3. Access this URL using browser
http://localhost:4200/

## System design:
Check the word document in base directory: General-Data-Query-Tool-Design.docx

## APIs specifications:
Swagger UI Link:
http://localhost:8080/swagger-ui/index.html#/

Method	| Path	| Description	| 
------------- | ------------------------- | ------------- |
POST	| /general/data/query/datasource	| Register new DataSource and Load all tables.	          |	
POST	| /general/data/query/tables/{tableName}/records	| Search table records by criteria.	| 
