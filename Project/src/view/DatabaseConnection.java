package view;

import controller.DatabaseController;
import model.Recipe;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private DatabaseController dbController;
    public Connection connection;



    public DatabaseConnection() {
        this.connection = getDatabaseconnection();
    }

    public Connection getDatabaseconnection() {
        String url = "jdbc:postgresql://pgserver.mau.se:5432/cheapeat";
        String password = "8v2y4xyu";
        String user = "ao7830";

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

    public void endDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeQueryVoidReturn(String query) {


    }

    public ArrayList<Recipe> getRecipesForNewConnection(String query) {

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            return new ArrayList<>();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}




