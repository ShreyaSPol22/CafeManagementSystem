package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/cafedb";
    private static final String user = "root";
    private static final String password = "Parkar@123";

    public static Connection getConnection() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        }
        /*catch(ClassNotFoundException e)
        {
            System.err.println("JDBC Driver not found"+e.getMessage());
        }*/ catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to database");
        }
    }
}
