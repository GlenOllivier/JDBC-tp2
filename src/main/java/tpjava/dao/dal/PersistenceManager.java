package tpjava.dao.dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistenceManager {

    private final static int CONNECTION_TIMEOUT = 10;

    private static Connection connection;

    private PersistenceManager() {
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed() || !connection.isValid(CONNECTION_TIMEOUT)) {

            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream("resources/conf.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                System.out.println(e.getCause());
            }

            String login = properties.getProperty("jdbc.login");
            String password = properties.getProperty("jdbc.password");
            String url = properties.getProperty("jdbc.url");

            connection = DriverManager.getConnection(url, login, password);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}