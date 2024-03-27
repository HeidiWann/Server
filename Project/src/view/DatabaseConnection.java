package view;

import controller.DatabaseController;

import java.sql.*;

public class DatabaseConnection {
    public DatabaseConnection() {
        this.connection = getDatabaseconnection();
    }

    private DatabaseController dbController;
    private Connection connection;

    public Connection getDatabaseconnection() {
        String url = "jdbc:postgresql://pgserver.mau.se:5432/cheapeat";
        //String user = ""; //put your user here or put it in a .env file
        String user = System.getenv("DBUSER");
        //String password = ""; //put your password here or put it in a .env file
        String password = System.getenv("DBPASSWORD");
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

    //Todo är samma som connect?

    public void endConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}




