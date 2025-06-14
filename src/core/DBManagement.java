package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManagement {

    private static DBManagement instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/OrdersManagementSystem";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "M.alicolak01";

    private DBManagement(){
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance(){
        try {
            if(instance == null|| instance.getConnection().isClosed()){}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }
}
