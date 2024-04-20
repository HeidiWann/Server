package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;


/**
 * Clas that is responsible for handling the logic behind handling the database.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class DatabaseController {

    private DatabaseCommunicator databaseCommunicator;

    /**
     * Clas constructor
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public DatabaseController() {



    }

    //Usch detta är så komplex och svårt, fattar inte riktigt vad jag håller på med.
    public void addRecipe(String recipeName, byte[] recipeImage, String recipeInstructions, int authorId) throws SQLException {
        String sql = "{ CALL insert_into_recipes(?, ?, ?, ?) }";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, recipeName);
            stmt.setBytes(2, recipeImage);
            stmt.setString(3, recipeInstructions);
            stmt.setInt(4, authorId);
            stmt.execute();
        }
    }

    public ArrayList<Recipe> getAllRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                recipes.add(new Recipe(
                        resultSet.getInt("RecipeID"),
                        resultSet.getString("RecipeName"),
                        resultSet.getBytes("RecipeImage"),
                        resultSet.getString("RecipeInstructions")
                ));
            }
            statement.close();
            resultSet.close();
            databaseCommunicator.closeConnection();
        }
        return recipes;
    }

    public void addUser(String userName, String password) throws SQLException {
        String query = "{ CALL users(?, ?) }";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement statement = connection.prepareCall(query)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
        }
    }

    public boolean checkUserExists(String username) throws SQLException {
        String query = "{ ? = CALL userFound(?) }";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement statement = connection.prepareCall(query)) {
            statement.registerOutParameter(1, Types.BOOLEAN);
            statement.setString(2, username);
            statement.execute();
            return statement.getBoolean(1);
        }
    }

    /**
     * Method used for fetching the stored recipes from the database.
     * The method is used for newly connected clients
     *
     * @return An Arraylist containing recipes
     * @author Anton Jansson
     */
    /*public ArrayList<Recipe> getRecipesForNewConnection() {
        String query = "";
        ArrayList<Recipe> recipes = new ArrayList<>();

        ResultSet resultSet = databaseCommunicator.getResultSet(query);

        try {
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipes.add(recipe);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return recipes;
    }*/

    /**
     * Method used for fetching the stored users from the database.
     * The method is used for newly connected clients
     *
     * @return An Arraylist containing users
     * @author Anton Jansson
     */
   /* public ArrayList<User> getUsersForNewConnection() {
        String query = "";
        ArrayList<User> users = new ArrayList<>();

        ResultSet resultSet = databaseCommunicator.getResultSet(query);

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
   /* public void executeQueryVoidReturn(String query) {
        databaseCommunicator.executeQueryVoidReturn(query);
    }*/


}
