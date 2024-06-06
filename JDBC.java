<<<<<<< HEAD
import java.util.*;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.*;
import java.util.regex.*;

public class JDBC extends JFrame {
    private JTextField queryTextField;
    private JTextArea resultTextArea;

    public JDBC() {
        setTitle("SQL Query Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the input query
        JPanel queryPanel = new JPanel();
        queryTextField = new JTextField(30);
        queryPanel.add(queryTextField);

        // Create a button to validate and optimize the query
        JButton validateButton = new JButton("Validate");
        queryPanel.add(validateButton);

        // Create buttons for additional functionality
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        // Create a text area to display the results
        resultTextArea = new JTextArea(30, 30);
        resultTextArea.setEditable(false);

        // Create a panel for additional buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        add(queryPanel, BorderLayout.NORTH);
        add(resultTextArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the validate and optimize button
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originalQuery = queryTextField.getText();
                String optimizedQuery = optimizeSQLQuery(originalQuery);
                boolean isValid = validateSQLQuery(optimizedQuery);

                if (isValid) {
                    // Count the number of Joins, Subqueries, Aggregate Functions, and Conditions
                    int numJoins = countJoins(optimizedQuery);
                    int numSubqueries = countSubqueries(optimizedQuery);
                    int numAggregateFunctions = countAggregateFunctions(optimizedQuery);
                    int numConditions = countConditions(optimizedQuery);
                    //analyzeTokens(optimizedQuery);

                    // Check if the query is complex
                    boolean isComplex = numJoins > 2 || numSubqueries > 2 || numAggregateFunctions >= 2 || numConditions > 2;

                    // Display the validation result, optimized query, and complexity status in the text area
                    resultTextArea.setText("Original Query:\n" + originalQuery + "\n\n");
                    //resultTextArea.append("Optimized Query:\n" + optimizedQuery + "\n\n");
                    resultTextArea.append("Query is valid.\n\n");
                    
                    analyzeTokens(optimizedQuery);
                    resultTextArea.append("\nNumber of Joins: " + numJoins + "\n");
                    resultTextArea.append("Number of Subqueries: " + numSubqueries + "\n");
                    resultTextArea.append("Number of Aggregate Functions: " + numAggregateFunctions + "\n");
                    resultTextArea.append("Number of Conditions: " + numConditions + "\n\n");
                    resultTextArea.append("Complex Query: " + (isComplex ? "Yes, it's a complex query" : "No, it's not a complex query"));
                    
                    //analyzeTokens(optimizedQuery);
                    // Show a dialog with the query result as a table
                    String queryResult = executeQuery(optimizedQuery);
                    displayQueryResultTable(queryResult);
                } else {
                    resultTextArea.setText("Original Query:\n" + originalQuery + "\n\n");
                    resultTextArea.append("Query is invalid: " + getSQLErrorMessage() + "\n");
                }
            }
        });

        // Add action listener to the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryTextField.setText("");
                resultTextArea.setText("");
            }
        });

        // Add action listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set the frame to full screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setSize(800, 600); // Set a default size if full screen is not supported
        }
    }

    private String sqlErrorMessage = "";

    // Helper method to set SQL error message
    private void setSQLErrorMessage(String message) {
        sqlErrorMessage = message;
    }

    // Helper method to get SQL error message
    private String getSQLErrorMessage() {
        return sqlErrorMessage;
    }

    // Helper method to optimize SQL query (simplified example)
    private static String optimizeSQLQuery(String sqlQuery) {
        // Check if the query is a SELECT statement with "*"
        if (sqlQuery.trim().toUpperCase().startsWith("SELECT * FROM")) {
            // Replace "*" with the column names you want to retrieve
            return sqlQuery.replace("*", "column1, column2, column3");
        }
        return sqlQuery;
    }
     private void analyzeTokens(String sqlQuery) {
    java.util.List<String> keywords = java.util.Arrays.asList(
    "SELECT", "FROM", "WHERE", "JOIN", "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL JOIN", "GROUP BY", 
    "ORDER BY", "HAVING", "UNION", "INSERT", "UPDATE", "DELETE", "CREATE", "DROP", "ALTER", "DISTINCT", "AS"
);
 // Add more if needed
    java.util.List<String> literals = new java.util.ArrayList<>();
    java.util.List<String> operators = java.util.Arrays.asList("=", ">", "<", ">=", "<=", "<>", "+", "-", "*", "/"); // Add more as needed

    // Pattern for identifying literals (values enclosed in single quotes)
    Pattern literalPattern = Pattern.compile("'(.*?)'");

    // Split the query into tokens and analyze each token
    String[] tokens = sqlQuery.split("\\s+");

    for (String token : tokens) {
        // Check if the token is a keyword
        if (keywords.contains(token.toUpperCase())) {
            resultTextArea.append("Keyword: " + token + "\n");
        }
        // Check if the token is a literal
        else if (literalPattern.matcher(token).matches()) {
            literals.add(token);
            resultTextArea.append("Literal: " + token + "\n");
        }
        // Check if the token is an operator
        else if (operators.contains(token)) {
            resultTextArea.append("Operator: " + token + "\n");
        }
        // Check if the token is an identifier
        else {
            resultTextArea.append("Identifier: " + token + "\n");
        }
    }
}
    // Helper method to validate SQL query and store the results
private boolean validateSQLQuery(String sqlQuery) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/db2"; 
    String jdbcUser = "root"; 
    String jdbcPassword = "Sarvesh_3011"; 

    boolean isComplex = false; // Define the variable isComplex

    try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);

            // Store the validation results in the database
            String validationDetails = "Original Query:\n" + sqlQuery + "\n\n" +
                    "Query is valid.\n\n" +
                    "Number of Joins: " + countJoins(sqlQuery) + "\n" +
                    "Number of Subqueries: " + countSubqueries(sqlQuery) + "\n" +
                    "Number of Aggregate Functions: " + countAggregateFunctions(sqlQuery) + "\n" +
                    "Number of Conditions: " + countConditions(sqlQuery) + "\n\n" +
                    "Complex Query: " + (isComplex ? "Yes, it's a complex query" : "No, it's not a complex query");

            // Store the validation details in the database table 'query_validation_details'
            String insertValidationDetails = "INSERT INTO query_validation_details (validation_details) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertValidationDetails)) {
                preparedStatement.setString(1, validationDetails);
                preparedStatement.executeUpdate();
            }

            return true; // Query is valid
        }
    } catch (SQLException e) {
        setSQLErrorMessage(e.getMessage()); // Set SQL error message
        return false; // Query is invalid
    }
}


    // Helper method to execute the SQL query and return the result as a string
    private String executeQuery(String sqlQuery) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db2"; 
        String jdbcUser = "root"; 
        String jdbcPassword = "Sarvesh_3011"; 

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
            try (Statement statement = connection.createStatement()) {
                // Execute the query
                boolean hasResultSet = statement.execute(sqlQuery);

                // If the query returns a result set, process and return it as a string
                if (hasResultSet) {
                    StringBuilder resultBuilder = new StringBuilder();
                    ResultSet resultSet = statement.getResultSet();
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    int columnCount = metaData.getColumnCount();

                    // Append column names
                    for (int i = 1; i <= columnCount; i++) {
                        resultBuilder.append(metaData.getColumnName(i));
                        if (i < columnCount) {
                            resultBuilder.append("\t");
                        }
                    }
                    resultBuilder.append("\n");

                    // Append query results
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            resultBuilder.append(resultSet.getString(i));
                            if (i < columnCount) {
                                resultBuilder.append("\t");
                            }
                        }
                        resultBuilder.append("\n");
                    }

                    return resultBuilder.toString();
                } else {
                    // The query didn't return a result set (e.g., an INSERT, UPDATE, DELETE statement)
                    return "Query executed successfully.";
                }
            }
        } catch (SQLException e) {
            setSQLErrorMessage(e.getMessage()); // Set SQL error message
            return "Error executing the query: " + e.getMessage();
        }
    }

   

    // Helper method to count Joins
    private static int countJoins(String sqlQuery) {
        Pattern joinPattern = Pattern.compile("\\bJOIN\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = joinPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Subqueries
    private static int countSubqueries(String sqlQuery) {
        Pattern subqueryPattern = Pattern.compile("\\bSELEC\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = subqueryPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Aggregate Functions
    private static int countAggregateFunctions(String sqlQuery) {
        // You can add patterns for specific aggregate functions (e.g., COUNT, SUM, AVG, etc.) here
        Pattern aggregatePattern = Pattern.compile("\\bCOUNT\\b|\\bSUM\\b|\\bAVG\\b|\\\\bMIN\\\\b|\\\\bMAX\\\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = aggregatePattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Conditions (e.g., WHERE clauses)
    private static int countConditions(String sqlQuery) {
        Pattern conditionPattern = Pattern.compile("\\bWHERE\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = conditionPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to display query result as a table in a JOptionPane
    private void displayQueryResultTable(String queryResult) {
        String[] lines = queryResult.split("\n");
        int rowCount = lines.length;
        int columnCount = lines[0].split("\t").length;

        // Prepare data for the table model
        String[] columnNames = new String[columnCount];
        Object[][] data = new Object[rowCount - 1][columnCount]; // Subtract 1 for the header row

        // Parse the column names
        columnNames = lines[0].split("\t");

        // Parse the data rows
        for (int i = 1; i < rowCount; i++) {
            String[] values = lines[i].split("\t");
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = values[j];
            }
        }

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable resultTable = new JTable(tableModel);

        // Show the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Show the scroll pane in a JOptionPane
        JOptionPane.showMessageDialog(this, scrollPane, "Query Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JDBC().setVisible(true);
        });
    }
}
=======
import java.util.*;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.*;
import java.util.regex.*;

public class JDBC extends JFrame {
    private JTextField queryTextField;
    private JTextArea resultTextArea;

    public JDBC() {
        setTitle("SQL Query Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the input query
        JPanel queryPanel = new JPanel();
        queryTextField = new JTextField(30);
        queryPanel.add(queryTextField);

        // Create a button to validate and optimize the query
        JButton validateButton = new JButton("Validate");
        queryPanel.add(validateButton);

        // Create buttons for additional functionality
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        // Create a text area to display the results
        resultTextArea = new JTextArea(30, 30);
        resultTextArea.setEditable(false);

        // Create a panel for additional buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        add(queryPanel, BorderLayout.NORTH);
        add(resultTextArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the validate and optimize button
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originalQuery = queryTextField.getText();
                String optimizedQuery = optimizeSQLQuery(originalQuery);
                boolean isValid = validateSQLQuery(optimizedQuery);

                if (isValid) {
                    // Count the number of Joins, Subqueries, Aggregate Functions, and Conditions
                    int numJoins = countJoins(optimizedQuery);
                    int numSubqueries = countSubqueries(optimizedQuery);
                    int numAggregateFunctions = countAggregateFunctions(optimizedQuery);
                    int numConditions = countConditions(optimizedQuery);
                    //analyzeTokens(optimizedQuery);

                    // Check if the query is complex
                    boolean isComplex = numJoins > 2 || numSubqueries > 2 || numAggregateFunctions >= 2 || numConditions > 2;

                    // Display the validation result, optimized query, and complexity status in the text area
                    resultTextArea.setText("Original Query:\n" + originalQuery + "\n\n");
                    //resultTextArea.append("Optimized Query:\n" + optimizedQuery + "\n\n");
                    resultTextArea.append("Query is valid.\n\n");
                    
                    analyzeTokens(optimizedQuery);
                    resultTextArea.append("\nNumber of Joins: " + numJoins + "\n");
                    resultTextArea.append("Number of Subqueries: " + numSubqueries + "\n");
                    resultTextArea.append("Number of Aggregate Functions: " + numAggregateFunctions + "\n");
                    resultTextArea.append("Number of Conditions: " + numConditions + "\n\n");
                    resultTextArea.append("Complex Query: " + (isComplex ? "Yes, it's a complex query" : "No, it's not a complex query"));
                    
                    //analyzeTokens(optimizedQuery);
                    // Show a dialog with the query result as a table
                    String queryResult = executeQuery(optimizedQuery);
                    displayQueryResultTable(queryResult);
                } else {
                    resultTextArea.setText("Original Query:\n" + originalQuery + "\n\n");
                    resultTextArea.append("Query is invalid: " + getSQLErrorMessage() + "\n");
                }
            }
        });

        // Add action listener to the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryTextField.setText("");
                resultTextArea.setText("");
            }
        });

        // Add action listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set the frame to full screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setSize(800, 600); // Set a default size if full screen is not supported
        }
    }

    private String sqlErrorMessage = "";

    // Helper method to set SQL error message
    private void setSQLErrorMessage(String message) {
        sqlErrorMessage = message;
    }

    // Helper method to get SQL error message
    private String getSQLErrorMessage() {
        return sqlErrorMessage;
    }

    // Helper method to optimize SQL query (simplified example)
    private static String optimizeSQLQuery(String sqlQuery) {
        // Check if the query is a SELECT statement with "*"
        if (sqlQuery.trim().toUpperCase().startsWith("SELECT * FROM")) {
            // Replace "*" with the column names you want to retrieve
            return sqlQuery.replace("*", "column1, column2, column3");
        }
        return sqlQuery;
    }
     private void analyzeTokens(String sqlQuery) {
    java.util.List<String> keywords = java.util.Arrays.asList(
    "SELECT", "FROM", "WHERE", "JOIN", "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL JOIN", "GROUP BY", 
    "ORDER BY", "HAVING", "UNION", "INSERT", "UPDATE", "DELETE", "CREATE", "DROP", "ALTER", "DISTINCT", "AS"
);
 // Add more if needed
    java.util.List<String> literals = new java.util.ArrayList<>();
    java.util.List<String> operators = java.util.Arrays.asList("=", ">", "<", ">=", "<=", "<>", "+", "-", "*", "/"); // Add more as needed

    // Pattern for identifying literals (values enclosed in single quotes)
    Pattern literalPattern = Pattern.compile("'(.*?)'");

    // Split the query into tokens and analyze each token
    String[] tokens = sqlQuery.split("\\s+");

    for (String token : tokens) {
        // Check if the token is a keyword
        if (keywords.contains(token.toUpperCase())) {
            resultTextArea.append("Keyword: " + token + "\n");
        }
        // Check if the token is a literal
        else if (literalPattern.matcher(token).matches()) {
            literals.add(token);
            resultTextArea.append("Literal: " + token + "\n");
        }
        // Check if the token is an operator
        else if (operators.contains(token)) {
            resultTextArea.append("Operator: " + token + "\n");
        }
        // Check if the token is an identifier
        else {
            resultTextArea.append("Identifier: " + token + "\n");
        }
    }
}
    // Helper method to validate SQL query and store the results
private boolean validateSQLQuery(String sqlQuery) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/db2"; 
    String jdbcUser = ""; 
    String jdbcPassword = ""; 

    boolean isComplex = false; // Define the variable isComplex

    try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);

            // Store the validation results in the database
            String validationDetails = "Original Query:\n" + sqlQuery + "\n\n" +
                    "Query is valid.\n\n" +
                    "Number of Joins: " + countJoins(sqlQuery) + "\n" +
                    "Number of Subqueries: " + countSubqueries(sqlQuery) + "\n" +
                    "Number of Aggregate Functions: " + countAggregateFunctions(sqlQuery) + "\n" +
                    "Number of Conditions: " + countConditions(sqlQuery) + "\n\n" +
                    "Complex Query: " + (isComplex ? "Yes, it's a complex query" : "No, it's not a complex query");

            // Store the validation details in the database table 'query_validation_details'
            String insertValidationDetails = "INSERT INTO query_validation_details (validation_details) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertValidationDetails)) {
                preparedStatement.setString(1, validationDetails);
                preparedStatement.executeUpdate();
            }

            return true; // Query is valid
        }
    } catch (SQLException e) {
        setSQLErrorMessage(e.getMessage()); // Set SQL error message
        return false; // Query is invalid
    }
}


    // Helper method to execute the SQL query and return the result as a string
    private String executeQuery(String sqlQuery) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db2"; 
        String jdbcUser = ""; 
        String jdbcPassword = ""; 

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
            try (Statement statement = connection.createStatement()) {
                // Execute the query
                boolean hasResultSet = statement.execute(sqlQuery);

                // If the query returns a result set, process and return it as a string
                if (hasResultSet) {
                    StringBuilder resultBuilder = new StringBuilder();
                    ResultSet resultSet = statement.getResultSet();
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    int columnCount = metaData.getColumnCount();

                    // Append column names
                    for (int i = 1; i <= columnCount; i++) {
                        resultBuilder.append(metaData.getColumnName(i));
                        if (i < columnCount) {
                            resultBuilder.append("\t");
                        }
                    }
                    resultBuilder.append("\n");

                    // Append query results
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            resultBuilder.append(resultSet.getString(i));
                            if (i < columnCount) {
                                resultBuilder.append("\t");
                            }
                        }
                        resultBuilder.append("\n");
                    }

                    return resultBuilder.toString();
                } else {
                    // The query didn't return a result set (e.g., an INSERT, UPDATE, DELETE statement)
                    return "Query executed successfully.";
                }
            }
        } catch (SQLException e) {
            setSQLErrorMessage(e.getMessage()); // Set SQL error message
            return "Error executing the query: " + e.getMessage();
        }
    }

   

    // Helper method to count Joins
    private static int countJoins(String sqlQuery) {
        Pattern joinPattern = Pattern.compile("\\bJOIN\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = joinPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Subqueries
    private static int countSubqueries(String sqlQuery) {
        Pattern subqueryPattern = Pattern.compile("\\bSELEC\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = subqueryPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Aggregate Functions
    private static int countAggregateFunctions(String sqlQuery) {
        // You can add patterns for specific aggregate functions (e.g., COUNT, SUM, AVG, etc.) here
        Pattern aggregatePattern = Pattern.compile("\\bCOUNT\\b|\\bSUM\\b|\\bAVG\\b|\\\\bMIN\\\\b|\\\\bMAX\\\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = aggregatePattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to count Conditions (e.g., WHERE clauses)
    private static int countConditions(String sqlQuery) {
        Pattern conditionPattern = Pattern.compile("\\bWHERE\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = conditionPattern.matcher(sqlQuery);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Helper method to display query result as a table in a JOptionPane
    private void displayQueryResultTable(String queryResult) {
        String[] lines = queryResult.split("\n");
        int rowCount = lines.length;
        int columnCount = lines[0].split("\t").length;

        // Prepare data for the table model
        String[] columnNames = new String[columnCount];
        Object[][] data = new Object[rowCount - 1][columnCount]; // Subtract 1 for the header row

        // Parse the column names
        columnNames = lines[0].split("\t");

        // Parse the data rows
        for (int i = 1; i < rowCount; i++) {
            String[] values = lines[i].split("\t");
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = values[j];
            }
        }

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable resultTable = new JTable(tableModel);

        // Show the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Show the scroll pane in a JOptionPane
        JOptionPane.showMessageDialog(this, scrollPane, "Query Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JDBC().setVisible(true);
        });
    }
}
>>>>>>> 624152aa84f90f68bb0567170373eb5607dd850f
