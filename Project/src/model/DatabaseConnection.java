package model;

import controller.DatabaseController;

import java.sql.*;

/**
 * Clas that starts the connection to the database
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */

public class DatabaseConnection {
    private DatabaseController dbController;
    private Connection connection;


    /**
     * @author Heidi Wännman
     */
    public DatabaseConnection() {


    }

    /**
     * Method used for establishing a connection to the database
     *
     * @return Connection
     * @author Heidi Wännman
     */

    public Connection getDatabaseconnection() throws SQLException {

        String user = System.getenv("DBUSER");
        String password = System.getenv("DBPASSWORD");
        String url = "jdbc:postgresql://pgserver.mau.se:5432/cheapeat";


        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Established");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw e;
        }
    }


    //TODO behövs kanske inte ifall "con.close();" eller liknande används i metoderna
    public void endDatabaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}




