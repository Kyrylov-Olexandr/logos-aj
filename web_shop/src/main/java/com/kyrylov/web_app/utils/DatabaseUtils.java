package com.kyrylov.web_app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
    public static final String url = "jdbc:mysql://localhost:3306/online_shop";
    public static final String username = "root";
    public static final String password = "root";
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
