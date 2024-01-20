package com.mziuri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MESSENGER";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private static Connection connection;



    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
}