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

## Installation
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/Sarvesh30112002/SQL-Analyzer.git
   cd sql-analyzer
