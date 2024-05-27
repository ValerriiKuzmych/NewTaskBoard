package com.kuzmych.taskboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDataBaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TASKBOARD";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Attempt to establish a connection to the database
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Failed to connect to the database.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        } finally {
            // Close the connection if it was established
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close the connection: " + e.getMessage());
                }
            }
        }
    }
}