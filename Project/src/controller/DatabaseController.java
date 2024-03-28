package controller;

import model.Recipe;
import view.*;

import java.util.ArrayList;


public class DatabaseController {
    private DatabaseConnection databaseConnection;

    public DatabaseController() {
        databaseConnection = new DatabaseConnection();
    }


    public ArrayList<Recipe> getRecipesForNewConnection() {
        String query="";
        databaseConnection.getRecipesForNewConnection(query);

        return new ArrayList<>();
    }

    public void executeQuery(String query) {
        databaseConnection.executeQueryVoidReturn(query);
    }
}
