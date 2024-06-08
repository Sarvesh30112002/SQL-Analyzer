# SQL Analyzer
SQL Analyzer is a software that reads SQL queries, performs token analysis, and evaluates the complexity of the queries. The main goals of this project are to categorize and count different types of tokens, analyze query complexity factors such as joins, subqueries, aggregate functions, and conditions, and provide valuable insights into query. 


<hr>

### Overview 
The SQL Analyzer project is a Java-based application designed to analyze SQL queries, validate them, optimize their structure, and display various statistics about the query complexity. It includes both a command-line interface and a graphical user interface (GUI) built with Java Swing. This tool is useful for database administrators, developers, and anyone interested in understanding and optimizing their SQL queries.

<hr>

### Features
1. **Query Analysis**:
   - Tokenizes SQL queries and categorizes tokens as keywords, identifiers, literals, operators, or punctuation.
   - Counts occurrences of various SQL components like joins, subqueries, aggregate functions, and conditions.
   - Determines query complexity based on the counts of these components.

2. **Query Validation**:
   - Validates SQL queries using JDBC.
   - Provides detailed error messages for invalid queries.
   - Stores validation results in a MySQL database.

3. **Query Optimization**:
   - Optimizes SQL queries by replacing "*" with specific column names (simple example).

4. **Graphical User Interface**:
   - User-friendly interface for entering SQL queries.
   - Displays original and optimized queries, validation results, token analysis, and query complexity.
   - Shows query results in a tabular format.

<hr>

## Requirements
- Java Development Kit (JDK) 8 or later
- MySQL Database
- JDBC Driver for MySQL
- An IDE or text editor for Java development

<hr>

## Classes and Methods
SQLTokenAnalyzer.java
Main Class:
public static void main(String[] args): Reads queries from input.sql and analyzes them.
Helper Methods:
public static void analyzeQuery(String query): Analyzes the given SQL query and prints token counts and complexity analysis.
public static boolean isKeyword(String token): Checks if a token is a SQL keyword.
public static boolean isIdentifier(String token): Checks if a token is an identifier.
public static boolean isLiteral(String token): Checks if a token is a literal.
public static boolean isOperator(String token): Checks if a token is an operator.
public static boolean isPunctuation(String token): Checks if a token is punctuation.
public static int countOccurrences(String text, String keyword): Counts occurrences of a keyword in the text.
JDBC.java
Main Class:
public static void main(String[] args): Launches the GUI.
Helper Methods:
private boolean validateSQLQuery(String sqlQuery): Validates the SQL query and stores results in the database.
private String optimizeSQLQuery(String sqlQuery): Optimizes the SQL query (simple example).
private void analyzeTokens(String sqlQuery): Analyzes tokens in the SQL query and displays them in the GUI.
private String executeQuery(String sqlQuery): Executes the SQL query and returns the result as a string.
private static int countJoins(String sqlQuery): Counts JOIN clauses in the query.
private static int countSubqueries(String sqlQuery): Counts SELECT clauses in the query.
private static int countAggregateFunctions(String sqlQuery): Counts aggregate functions in the query.
private static int countConditions(String sqlQuery): Counts WHERE clauses in the query.
private void displayQueryResultTable(String queryResult): Displays query result in a table format.

<hr>

## Demo
<p align="center">
  <img src="Demo/Screenshot (781).png" alt="image"/>
</p>

<p align="center">
  <img src="Demo/Screenshot 2024-06-06 114003.png" alt="image"/>
</p>
