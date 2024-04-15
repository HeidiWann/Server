package controller;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Clas that is responsible for handling the logic behind handling the database.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class DatabaseController {
    private DatabaseConnection databaseConnection;
    private DatabaseCommunicator databaseCommunicator;

    /**
     * Clas constructor
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public DatabaseController() {
        databaseConnection = new DatabaseConnection();
        databaseCommunicator = new DatabaseCommunicator(databaseConnection);

    }
//Nu kommer det svåra hur vi ska få all information att fundera från DB, Server, Client utan strul
    public void addRecipe(Recipe recipe) throws SQLException {
        String query = "INSERT INTO recipes (RecipeName, RecipeImage, RecipeInstructions) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, recipe.getRecipeName());
            preparedStatement.setBytes(2, recipe.getRecipeImage());
            preparedStatement.setString(3, recipe.getRecipeInstructions());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes"; //kan behöva ändras så att man hämtar vid specific id
        try (Statement statement = databaseCommunicator.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                recipes.add(new Recipe(resultSet.getInt("RecipeID"), resultSet.getString("RecipeName"), resultSet.getBytes("RecipeImage"), resultSet.getString("RecipeInstructions")));
            }
            statement.close();
            statement.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;


    }

    public void updateRecipe(Recipe recipe) throws SQLException {
        String query = "UPDATE Recipes SET RecipeName = ?, RecipeImage = ?, RecipeInstructions = ? WHERE RecipeID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, recipe.getRecipeName());
            preparedStatement.setBytes(2, recipe.getRecipeImage()); //kan behövas ändras.
            preparedStatement.setString(3, recipe.getRecipeInstructions());
            preparedStatement.setInt(4, recipe.getRecipeID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();

        }


    }

    public void deleteRecipe(Recipe recipe) throws SQLException {
        String query = "DELETE FROM Recipes WHERE RecipeID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, recipe.getRecipeID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();

        }
    }

    public void addUser(String userName, String password) throws SQLException {
        String query = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public User getUserById(int userId) throws SQLException {
        User user = null;
        String query = "SELECT * FROM Users"; //kan behöva åndras för att man åndrat vid specific id
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("UserID"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")
                );
            }
            preparedStatement.close();
            preparedStatement.getConnection().close();
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE Users SET Username = ?, Password = ? WHERE UserID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeUser(User user) throws SQLException {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void addIngredient(String ingredientName, String store, double cost) throws SQLException {
        String query = "INSERT INTO Ingredients (IngredientName, Store, IngredientCost) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, ingredientName);
            preparedStatement.setString(2, store);
            preparedStatement.setDouble(3, cost);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();

        }
    }

    public void getAllIngredients() {
        String query = "SELECT * FROM Ingredients"; //kan behöva ändras för att man ändrat vid specific id
        try (Statement statement = databaseCommunicator.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("IngredientName"));
            }
            statement.close();
            statement.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void updateIngredient(Ingredient ingredient) throws SQLException {
        String query = "UPDATE Ingredients SET IngredientName = ?, Store = ?, IngredientCost = ? WHERE IngredientID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, ingredient.getIngredientName());
            preparedStatement.setString(2, ingredient.getStore());
            preparedStatement.setDouble(3, ingredient.getIngredientCost());
            preparedStatement.setInt(4, ingredient.getIngredientID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeIngredient(Ingredient ingredient) throws SQLException {
        String query = "DELETE FROM Ingredients WHERE IngredientID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, ingredient.getIngredientID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void addFood(String foodName) throws SQLException {
        String query = "INSERT INTO Food (FoodName) VALUES (?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, foodName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public Recipe getAllFoods(int foodId) throws SQLException { //Vet inte hur jag annars ska lösa detta utan en Food klass, känner jag behöver hjälp att fatta om det ska utföras på annat vis.
        Recipe food = null;
        String query = "SELECT * FROM Food"; //kan behöva ändras för att man ändrat vid specific id
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, foodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                food = new Recipe(
                        resultSet.getInt("FoodID"),
                        resultSet.getString("FoodName"));
            }
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
        return food;
    }

    public void updateFood(Recipe food) throws SQLException {
        String query = "UPDATE Food SET FoodName = ? WHERE FoodID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setString(1, food.getFoodName());
            preparedStatement.setInt(2, food.getFoodID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeFood(Recipe food) throws SQLException {
        String query = "DELETE FROM Food WHERE FoodID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, food.getFoodID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void addFavoriteRecipe(int userId, int recipeId) throws SQLException {
        String query = "INSERT INTO Favorites (UserID, RecipeID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public Recipe getFavoriteRecipe(int theUserId, int theRecipeId) throws SQLException {
        Recipe favoriteRecipe = null;
        String query = "SELECT * FROM FavoriteRecipes"; //kan behöva åndras för att man ändrat vid specific id
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) { //vet inte hur jag ska lösa detta, tror jag är för trött
            preparedStatement.setInt(1, theUserId);
            preparedStatement.setInt(2, theRecipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                favoriteRecipe = new Recipe(
                        resultSet.getInt("RecipeID"),
                        resultSet.getString("RecipeName"));
            }
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
        return favoriteRecipe;
    }

    public void uppdateFavoriteRecipe(Recipe favoriteRecipe) throws SQLException {
        String query = "UPDATE Favorites SET RecipeID = ? WHERE UserID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, favoriteRecipe.getRecipeID());
            preparedStatement.setInt(2, favoriteRecipe.getUserID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeFavoriteRecipe(Recipe favoriteRecipe) throws SQLException {
        String query = "DELETE FROM Favorites WHERE UserID = ? AND RecipeID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, favoriteRecipe.getUserID());
            preparedStatement.setInt(2, favoriteRecipe.getRecipeID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void addFoodCollection(int recipeId, int foodId) throws SQLException {
        String query = "INSERT INTO FoodCollection (RecipeID, FoodID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, foodId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }

    }

    public Recipe getFoodCollection(int recipeId, int foodId) throws SQLException {
        Recipe foodCollection = null;
        String query = "SELECT * FROM FoodCollection";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, foodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foodCollection = new Recipe(
                        resultSet.getInt("RecipeID"),
                        resultSet.getString("RecipeName"));
            }
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
        return foodCollection;
    }

    public void uppdateFoodCollection(Recipe foodCollection) throws SQLException {
        String query = "UPDATE FoodCollection SET RecipeID = ? WHERE FoodID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, foodCollection.getRecipeID());
            preparedStatement.setInt(2, foodCollection.getFoodID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeFoodCollection(Recipe foodCollection) throws SQLException {
        String query = "DELETE FROM FoodCollection WHERE RecipeID = ? AND FoodID = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, foodCollection.getRecipeID());
            preparedStatement.setInt(2, foodCollection.getFoodID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void addMeasurement(int measureId, String measure) throws SQLException {
        String query = "INSERT INTO Measurement (MeasureID, Measure) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, measureId);
            preparedStatement.setString(2, measure);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public Ingredient getMeasurement(int measureId) throws SQLException {
        Ingredient measurement = null;
        String query = "SELECT * FROM Measurement";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, measureId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                measurement = new Ingredient(
                        resultSet.getInt("MeasureID"),
                        resultSet.getString("Measure"));
            }
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
        return measurement;
    }

    public void uppdateMeasurement(Ingredient measurement) throws SQLException {
        String query = "UPDATE Measurement SET MeasureID = ? WHERE Measure = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, measurement.getMeasureID());
            preparedStatement.setString(2, measurement.getMeasure());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
    }

    public void removeMeasurement(Ingredient measurement) throws SQLException {
        String query = "DELETE FROM Measurement WHERE MeasureID = ? AND Measure = ?";
        try (PreparedStatement preparedStatement = databaseCommunicator.prepareStatement(query)) {
            preparedStatement.setInt(1, measurement.getMeasureID());
            preparedStatement.setString(2, measurement.getMeasure());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.getConnection().close();
        }
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
    public void executeQueryVoidReturn(String query) {
        databaseCommunicator.executeQueryVoidReturn(query);
    }


}
