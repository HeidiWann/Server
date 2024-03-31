package model;



import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasCommunicator {


    private DatabaseConnection databaseConnection;

    public DatabasCommunicator(DatabaseConnection databaseConnection){
        this.databaseConnection=databaseConnection;
    }
    public void executeQueryVoidReturn(String query) {
    }

    public ResultSet getResultSet(String query) {



        try (Connection connection=databaseConnection.getDatabaseconnection(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
