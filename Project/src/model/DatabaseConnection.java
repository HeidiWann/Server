package model;
import java.sql.*;

/**
 * Clas that starts the connection to the database
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class DatabaseConnection {
    private Connection connection;
    private DatabaseCommunicator databaseCommunicator;


    /**
     * @author Heidi Wännman
     */
    public DatabaseConnection(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
    }
    /**
     * Method used for establishing a connection to the database
     *
     * @return Connection
     * @author Heidi Wännman
     */
    public Connection getDatabaseconnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            String url = "jdbc:postgresql://pgserver.mau.se:5432/cheapeat";
            this.connection = DriverManager.getConnection(url, "", "");
            System.out.println("Connection Established");
        }
        return this.connection;
    }
}