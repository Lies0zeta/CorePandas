# CorePandas
A Pandas Library built in Java realized by Narek Davtyan and Lies Hadjadj.

## A first-year master project :mortar_board: :one:  


This is a project to build a Pandas library using Java  
This is a maven project:

The directories included in this repo:  
* src/main/java/CorePandas/CorePandas: The project source directory  
	- Main: Test file
	** data_structures: The library directory  
	- BadIndexException.java: Custom exception for row/column index  
	- Index.java: Row/Column index  
	- Data.java: Data structure   
	- DataFrame.java: DataFrame structure (uses Data and row+column Index)
* src/test/java/CorePandas/CorePandas: The project test directory  
	- BasicTests.java: Basic tests for DataFrame structure
- pom.xml : Maven file
- .travis.yml : TravisCI continuous integration file
- README.md : this file
- Dockerfile : script that creates an image for DHT
- LICENSE : target defined script to build and run code
* files: sample CSV files for tests
	- addresses.csv
	- cities.csv
	- taxables.csv
	- test.csv
	

## Features:  
- DataFrame creation:  
	- `DataFrame()`: builds an empty dataframe   
	- `DataFrame(final Object... columnsIndex)`: builds an empty dataframe with provided column index  
	- `DataFrame(final Collection<?> columnsIndex)`  : builds an empty dataframe with provided column index  
	- `DataFrame(final Collection<?> rowsIndex, final Collection<?> columnsIndex)`: builds an empty dataframe with provided column and row index  
	- `DataFrame(final List<? extends List<? extends Comparable<?>>> rawData)`: builds a dataframe with provided data  
	- `DataFrame(final Collection<?> rowsIndex, final Collection<?> columnsIndex, final List<? extends List<? extends Comparable<?>>> rawData)`: builds a dataframe with provided data, column and raw index  
	- `DataFrame(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex)`: builds a dataframe from a csv file with or without column and raw index
	- `static DataFrame createOneRow(final Object... rawData)`: builds a dataframe with one row from array of objects  
	- `static DataFrame createOneColumn(final Object... rawData)`: builds a dataframe with one column from array of objects  
	- `static DataFrame createColumns(final List<? extends Comparable<?>>... rawData)`: builds a dataframe with columns from array of columns    
	- `static DataFrame readCSV(String filePath)`: builds a dataframe from a csv file with column index and without raw index  
	- `static DataFrame readCSV(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex)`: builds a dataframe from a csv file with or without column and raw index  
	- If row/column index is empty, the generic integer index is created (from 0 to size-1) 
- Print DataFrame to screen:  
	- `void print()`: prints the complete dataframe  
	- `void printFirst()`: prints the first four lines of the dataframe  
	- `void printLast()`: prints the last four lines of the dataframe  
	- `void printLine(final Object lineNumber)`: prints the specified line taken as a row index of the dataframe   
	- `void printLines(final Object... lines)`: prints the specified lines taken as row indexes of the dataframe  
	- `static void print(DataFrame dataFrame)`: prints the complete dataframe  
	- `static void printFirst(DataFrame dataFrame)`: prints the first four lines of the dataframe  
	- `static void printLast(DataFrame dataFrame)`: prints the last four lines of the dataframe  
	- `static void printLine(DataFrame dataFrame, Object lineNumber)`: prints the specified line taken as a row index of the dataframe  
	- `static void printLines(DataFrame dataFrame, final Object... lineNumbers)`: prints the specified lines taken as row indexes of the dataframe  
- Selecting rows/columns in DataFrame:  
	- `List<? extends List<? extends Comparable<?>>> selectColumns(final Object... columnIds)`: selects and returns existing columns in the dataframe  
	- If the column is absent, it is ignored and the small message is printed on the screen  
	- `List<? extends List<? extends Comparable<?>>> selectRows(final Object... rowIds)`: selects and returns existing rows in the dataframe  
	- If the row is absent, it is ignored and the small message is printed on the screen  
- Creating new DataFrame from rows/columns in existing DataFrame:  
	- `DataFrame createFromColumns(final Object... columnIds)`: creates and returns new dataframe from columns in existing dataframe  
	- `DataFrame createFromRows(final Object... rowIds)`: creates and returns new dataframe from rows in existing dataframe  
	- `static DataFrame createFromColumns(DataFrame dSource, final Object... columnIds)`: creates and returns new dataframe from columns in existing dataframe  
	- `static DataFrame createFromRows(DataFrame dSource, final Object... rowIds)`: creates and returns new dataframe from rows in existing dataframe     
- Statistics in DataFrame:  
	- `T getMin(String columnName)`: computes and returns the minimum element in the given column (can be used with numeric types as well as with strings)  
	- `T getMax(String columnName)`: computes and returns the maximum element in the given column (can be used with numeric types as well as with strings)
	- `Double getMean(String columnName)`: computes and returns the mean element in the given column (can be used only with numeric types)  
- Getting some other basic information on DataFrame:  
	- `Set<Object> getColumnIndex()`: gets column index as set  
	- `Set<Object> getRowIndex()`: gets row index as set  
	- `<E> Set<E> getCol(final Object columnId)`: gets column as set with specified column index item  
	- `int size()`: gets number of columns in dataframe  

## Installation and Deployment  
### Description and Requirements
The project is built with Makefile that uses Dockerfile  

Software requirements:  
- having `docker` installed (https://docs.docker.com/install/)  
- having `maven` installed (and its numerous dependencies)  
- having `make` installed (and its numerous dependencies)  
- having `bash` terminal (on Windows you can use WSL bash)  

### Compilation/Building  
* Step 1: build the project from sources:  
`mvn compile`  

### Testing
* Step 1: testing the project with defined test:  
`mvn test`
