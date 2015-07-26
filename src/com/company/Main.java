package com.company;

import java.sql.*;

public class Main {
    //JDBC driver name and DB url
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/test";

    //DB credentials
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {
	// write your code here
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //register JDBC DRIVER
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            //by default only one resultset object per statement can be open at the same time
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            resultSet = statement.executeQuery("SELECT * from employees");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumnCount = metaData.getColumnCount();
            System.out.println();

            //display the tables getColumnName
            for (int i = 1; i<=numberOfColumnCount; i++){
                System.out.printf("%-10s\t",metaData.getColumnName(i));
            }
            System.out.println();

            //initial cursor positioned before the first row
            while (resultSet.next()) {//move the cursor forward one row
                for (int i = 1; i<=numberOfColumnCount; i++){
                    System.out.printf("%-10s\t", resultSet.getObject(i));
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();

            }catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Great! Everything works!");
    }
}
