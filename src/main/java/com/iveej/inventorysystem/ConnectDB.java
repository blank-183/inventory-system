package com.iveej.inventorysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
    private static final String USERNAME = "iveej";
    private static final String PASSWORD = "ETx!6q4rz!7wX";

    private ConnectDB() {}

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected successfully!");
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
