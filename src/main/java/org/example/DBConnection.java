package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/franchise_management"; // DB URL
    private static final String USER = "root"; // MySQL 사용자 이름
    private static final String PASSWORD = "ltg@@002545"; // MySQL 비밀번호

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB연결에 실패했습니다.");
            e.printStackTrace();
        }
        return connection;
    }
}