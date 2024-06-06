<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SQLTokenAnalyzer {
    public static void main(String[] args) {
        String inputFile = "input.sql";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                analyzeQuery(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void analyzeQuery(String query) {
        Map<String, Integer> tokenCounts = new HashMap<>();
        int numJoins = 0;
        int numSubqueries = 0;
        int numAggregateFunctions = 0;
        int numConditions = 0;

        StringTokenizer tokenizer = new StringTokenizer(query, " ,;()=*<>!+-/\t\n\r\f", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toUpperCase().trim();

            if (!token.isEmpty()) {
                tokenCounts.put(token, tokenCounts.getOrDefault(token, 0) + 1);
                
                if (isKeyword(token)) {
                    token = "Keyword: " + token;
                } else if (isIdentifier(token)) {
                    token = "Identifier: " + token;
                } else if (isLiteral(token)) {
                    token = "Literal: " + token;
                } else if (isOperator(token)) {
                    token = "Operator: " + token;
                } else if (isPunctuation(token)) {
                    token = "Punctuation: " + token;
                }
            }
        }
        numJoins = countOccurrences(query, "JOIN");
        numSubqueries = countOccurrences(query, "SELECT");
        numAggregateFunctions = countOccurrences(query, "COUNT") +
                                countOccurrences(query, "SUM") +
                                countOccurrences(query, "AVG") +
                                countOccurrences(query, "MAX") +
                                countOccurrences(query, "MIN");
        numConditions = countOccurrences(query, "WHERE");

        System.out.println("Token Counts and Complexity Analysis for Query: " + query);
        for (Map.Entry<String, Integer> entry : tokenCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Number of Joins: " + numJoins);
        System.out.println("Number of Subqueries: " + numSubqueries);
        System.out.println("Number of Aggregate Functions: " + numAggregateFunctions);
        System.out.println("Number of Conditions: " + numConditions);
        System.out.println();
    }

    public static boolean isKeyword(String token) {
        String[] keywords = {"SELECT", "FROM", "WHERE", "JOIN", "COUNT", "SUM", "AVG", "MAX", "MIN"};
        for (String keyword : keywords) {
            if (token.equals(keyword)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public static boolean isLiteral(String token) {
        return token.matches("\\d+") || token.matches("\".*\"");
    }

    public static boolean isOperator(String token) {
        String[] operators = {"=", "<", ">", "<=", ">=", "<>", "+", "-", "*", "/", "AND", "OR", "NOT"};
        for (String operator : operators) {
            if (token.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPunctuation(String token) {
        String[] punctuation = {",", ";", "(", ")", "*", "=", "<", ">", "<=", ">=", "<>"};
        for (String mark : punctuation) {
            if (token.equals(mark)) {
                return true;
            }
        }
        return false;
    }
    public static int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = text.indexOf(keyword);
        while (index != -1) {
            count++;
            index = text.indexOf(keyword, index + 1);
        }
        return count;
    }
}
=======
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SQLTokenAnalyzer {
    public static void main(String[] args) {
        String inputFile = "input.sql";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                analyzeQuery(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void analyzeQuery(String query) {
        Map<String, Integer> tokenCounts = new HashMap<>();
        int numJoins = 0;
        int numSubqueries = 0;
        int numAggregateFunctions = 0;
        int numConditions = 0;

        StringTokenizer tokenizer = new StringTokenizer(query, " ,;()=*<>!+-/\t\n\r\f", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toUpperCase().trim();

            if (!token.isEmpty()) {
                tokenCounts.put(token, tokenCounts.getOrDefault(token, 0) + 1);
                
                if (isKeyword(token)) {
                    token = "Keyword: " + token;
                } else if (isIdentifier(token)) {
                    token = "Identifier: " + token;
                } else if (isLiteral(token)) {
                    token = "Literal: " + token;
                } else if (isOperator(token)) {
                    token = "Operator: " + token;
                } else if (isPunctuation(token)) {
                    token = "Punctuation: " + token;
                }
            }
        }
        numJoins = countOccurrences(query, "JOIN");
        numSubqueries = countOccurrences(query, "SELECT");
        numAggregateFunctions = countOccurrences(query, "COUNT") +
                                countOccurrences(query, "SUM") +
                                countOccurrences(query, "AVG") +
                                countOccurrences(query, "MAX") +
                                countOccurrences(query, "MIN");
        numConditions = countOccurrences(query, "WHERE");

        System.out.println("Token Counts and Complexity Analysis for Query: " + query);
        for (Map.Entry<String, Integer> entry : tokenCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Number of Joins: " + numJoins);
        System.out.println("Number of Subqueries: " + numSubqueries);
        System.out.println("Number of Aggregate Functions: " + numAggregateFunctions);
        System.out.println("Number of Conditions: " + numConditions);
        System.out.println();
    }

    public static boolean isKeyword(String token) {
        String[] keywords = {"SELECT", "FROM", "WHERE", "JOIN", "COUNT", "SUM", "AVG", "MAX", "MIN"};
        for (String keyword : keywords) {
            if (token.equals(keyword)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public static boolean isLiteral(String token) {
        return token.matches("\\d+") || token.matches("\".*\"");
    }

    public static boolean isOperator(String token) {
        String[] operators = {"=", "<", ">", "<=", ">=", "<>", "+", "-", "*", "/", "AND", "OR", "NOT"};
        for (String operator : operators) {
            if (token.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPunctuation(String token) {
        String[] punctuation = {",", ";", "(", ")", "*", "=", "<", ">", "<=", ">=", "<>"};
        for (String mark : punctuation) {
            if (token.equals(mark)) {
                return true;
            }
        }
        return false;
    }
    public static int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = text.indexOf(keyword);
        while (index != -1) {
            count++;
            index = text.indexOf(keyword, index + 1);
        }
        return count;
    }
}
>>>>>>> 624152aa84f90f68bb0567170373eb5607dd850f
