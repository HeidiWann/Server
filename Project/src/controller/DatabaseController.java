package controller;

import model.Recipe;
import model.User;
import view.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseController {
    private DatabaseConnection databaseConnection;
    private DatabasCommunicator databasCommunicator;

    public DatabaseController() {
        databaseConnection = new DatabaseConnection();
        databasCommunicator = new DatabasCommunicator(databaseConnection);

    }


    public ArrayList<Recipe> getRecipesForNewConnection() {
        String query = "";
        ArrayList<Recipe> recipes = new ArrayList<>();

        ResultSet resultSet = databasCommunicator.getResultSet(query);

        try {
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipes.add(recipe);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return recipes;
    }

    public ArrayList<User> getUsersForNewConnection() {
        String query = "";
        ArrayList<User> users = new ArrayList<>();

        ResultSet resultSet = databasCommunicator.getResultSet(query);

        try {
            while (resultSet.next()) {
                User user = new User();
                users.add(user);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return users;
    }

    public void executeQuery(String query) {
        databasCommunicator.executeQueryVoidReturn(query);
    }


}
