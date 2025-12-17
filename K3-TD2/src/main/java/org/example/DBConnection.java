package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private  String JDBC_URL = System.getenv("JDBC_URL");
    private  String USERNAME = System.getenv("USERNAME");
    private  String PASSWORD = System.getenv("PASSWORD");

    public  Connection getDBConnection() throws SQLException {

        if (JDBC_URL == null || USERNAME == null || PASSWORD == null) {
            throw new RuntimeException();
        }
        System.out.println("Connected");
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
