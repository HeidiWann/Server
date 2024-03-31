package controller;

import model.DatabasCommunicator;
import model.DatabaseConnection;
import model.Recipe;
import model.User;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Clas that is responsible for handling the logic behind handling the database.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class DatabaseController {
    private DatabaseConnection databaseConnection;
    private DatabasCommunicator databasCommunicator;

    /**
     * Clas constructor
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public DatabaseController() {
        databaseConnection = new DatabaseConnection();
        databasCommunicator = new DatabasCommunicator(databaseConnection);

    }


    /**
     * Method used for fetching the stored recipes from the database.
     * The method is used for newly connected clients
     *
     * @return An Arraylist containing recipes
     * @author Anton Jansson
     */
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

    /**
     * Method used for fetching the stored users from the database.
     * The method is used for newly connected clients
     *
     * @return An Arraylist containing users
     * @author Anton Jansson
     */
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

    /**
     * A generic method for executing querys.
     *
     * @param query
     * @return void
     * @author Anton Jansson
     */
    public void executeQueryVoidReturn(String query) {
        databasCommunicator.executeQueryVoidReturn(query);
    }


}
