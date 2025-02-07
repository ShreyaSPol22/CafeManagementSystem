package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseConnection {
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final String url = "jdbc:mysql://localhost:3306/cafedb";
    private static final String user = "root";
    private static final String password = "Parkar@123";

    private DatabaseConnection() {

    }

    public static Connection getInstance() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                LOGGER.info("Database connection established");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error in Database Connection" + e.getMessage());
            }
        }
        return connection;
    }
}
