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
        }
        return recipes;
    }

    //Denna ska användas via UserController.
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
     * Method used for fetching users from the database
     *
     * @return An ArrayList of users
     * @author Anton Jansson
     */
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = databaseCommunicator.getResultSet("SELECT user_id, username, role FROM users");
        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("role")));
        }
        resultSet.close();
        return users;
    }

    /**
     * Method used for when a user updates it profile
     *
     * @param user User
     * @author Anton Jansson
     */
    public void updateProfile(User user) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
        Object[] params = new Object[]{user.getUserName(), user.getPassword(), user.getUserID()};
        databaseCommunicator.executeUpdate(query, params);
    }

    /**
     * Method used for when a client registers to the database
     *
     * @param user User
     * @author Anton Jansson
     */
    public void register(User user) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        Object[] params = {user.getUserName(), user.getPassword(), user.getRole()};
        databaseCommunicator.executeUpdate(query, params);
    }
}
