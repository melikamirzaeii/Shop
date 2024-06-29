
// Import necessary classes for handling SQL connections
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database URL, username, and password are defined as constants
    private static final String URL = "jdbc:derby://localhost:1527/shopmedb"; // URL for connecting to the Derby database
    private static final String USER = "root"; // Username for the database
    private static final String PASSWORD = "root"; // Password for the database

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        // Get a connection to the database using the specified URL, username, and password
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
