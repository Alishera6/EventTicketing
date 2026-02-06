package data.postgres;

import config.DatabaseConfig;
import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    @Override
    public Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
}
