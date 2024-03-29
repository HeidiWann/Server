package view;

import controller.DatabaseController;

import java.sql.*;

public class DatabaseConnection {
    private DatabaseController dbController;


    public DatabaseConnection() {

    }

    public Connection getDatabaseconnection() {
        String user = System.getenv("DBUSER");
         String password = System.getenv("DBPASSWORD");
        String url = "jdbc:postgresql://pgserver.mau.se:5432/cheapeat";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Established");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

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




