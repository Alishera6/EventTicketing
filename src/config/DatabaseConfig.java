package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static DatabaseConfig instance;

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig() {
        this.url = System.getenv("DB_URL");
        this.user = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASSWORD");
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    // IMPORTANT: returns NEW connection each time (so try-with-resources can close it safely)
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
