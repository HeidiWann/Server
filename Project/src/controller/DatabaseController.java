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
    public DatabaseController(DatabaseCommunicator databaseCommunicator) throws SQLException {
        this.databaseCommunicator = databaseCommunicator;
    }

    /**
     * @param recipe
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void userAddRecipe(Recipe recipe) {
        try {
            addFood(recipe.getRecipeName());
            addRecipe(recipe);
            ArrayList<Ingredient> ingredients = recipe.getDish().getIngredients();
            int foodId = getFoodId(recipe.getRecipeName());
            for (Ingredient ingredient : ingredients) {
                addIngredientToRecipe(ingredient, foodId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Usch detta är så komplex och svårt, fattar inte riktigt vad jag håller på med. Denna ska användas via RecipeController.
    public void addRecipe(String recipeName, byte[] recipeImage, String recipeInstructions, int authorId) throws SQLException {
        String sql = "CALL insertnewrecipe(?, ?, ?, ?,?)";
        addFood(recipeName);
        int foodId = getFoodId(recipeName);
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, recipeName);
            stmt.setBytes(2, recipeImage);
            stmt.setString(3, recipeInstructions);
            stmt.setInt(4, authorId);
            stmt.setInt(5, foodId);
            stmt.execute();
        }
    }

    /**
     * @param recipe
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR förutom att lösa imageview
     */
    public void addRecipe(Recipe recipe) throws SQLException {
        String sql = "CALL insertnewrecipe(?, ?, ?, ?,?)";
        String recipeName = recipe.getRecipeName();
        byte[] recipeImage = recipe.getImageOfRecipe();

        String recipeInstructions = recipe.getInstructions();
        int authorId = getUserID(recipe.getAuthor());
        int foodId = getFoodId(recipe.getRecipeName());
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, recipeName);
            stmt.setBytes(2, recipeImage);
            stmt.setString(3, recipeInstructions);
            stmt.setInt(4, authorId);
            stmt.setInt(5, foodId);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param foodName
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addFood(String foodName) throws SQLException {
        String sql = "CALL insertnewfood(?)";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, foodName);
            stmt.execute();
        }
    }

    /**
     * @param foodName
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getFoodId(String foodName) throws SQLException {
        String sql = "SELECT foodid FROM food where foodname = ?";
        int foodId = -1;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, foodName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    foodId = resultSet.getInt("foodid");
                }
            }
        }
        return foodId;
    }

    /**
     * @param ingredient
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addIngredient(Ingredient ingredient) throws SQLException {
        String sql = "CALL insertingredient(?,?,?)";
        String nameOfIngredient = ingredient.getIngredientName();
        Store nameOfStore = ingredient.getStore();
        Double costOfIngredient = ingredient.getIngredientCost();
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, nameOfIngredient);
            stmt.setString(2, nameOfStore.toString());
            stmt.setDouble(3, costOfIngredient);
            stmt.execute();
        }
    }

    /**
     * @param ingredientName
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getIngredientId(String ingredientName) throws SQLException {
        String sql = "Select ingredientid FROM ingredients where ingredientname=?";
        int ingredientId = -1;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, ingredientName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    ingredientId = resultSet.getInt("ingredientid");
                }
            }
        }
        return ingredientId;
    }

    /**
     * @param ingredient
     * @param foodId
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addIngredientToRecipe(Ingredient ingredient, int foodId) throws SQLException {
        String sql = "CALL addingredienttofood(?,?,?,?)";
        int ingredientId = getIngredientId(ingredient.getIngredientName());
        double amountOfIngredient = ingredient.getAmountOfIngredient();
        int measurementid = getMeasureId(ingredient.getMeasure());
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setDouble(1, amountOfIngredient);
            stmt.setInt(2, measurementid);//Måste ändras
            stmt.setInt(3, ingredientId);
            stmt.setInt(4, foodId);
            stmt.execute();
        }
    }

    /**
     * @return
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public ArrayList<Recipe> getRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT recipename, recipeimage, recipeinstructions, username, foodname, ingredientname, store, ingredientcost, amountofingredient, measure FROM getalltherecipes()";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String recipeName = resultSet.getString("recipename");
                byte[] recipeImage = resultSet.getBytes("recipeimage");
                String instructions = resultSet.getString("recipeinstructions");
                String author = resultSet.getString("username");
                String foodName = resultSet.getString("foodname");

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                do {
                    String ingredientName = resultSet.getString("ingredientname");
                    Store store = Store.valueOf(resultSet.getString("store"));
                    double price = resultSet.getDouble("ingredientcost");
                    double amount = resultSet.getDouble("amountofingredient");
                    Measurement measure = Measurement.valueOf(resultSet.getString("measure"));
                    Ingredient ingredient = new Ingredient(ingredientName, store, price, amount, measure);
                    ingredients.add(ingredient);
                } while (resultSet.next() && recipeName.equals(resultSet.getString("recipename")));

                if(!resultSet.isBeforeFirst()){
                    resultSet.previous();
                }
                Recipe recipe = new Recipe(author, instructions, recipeImage, ingredients, foodName, null);
                recipes.add(recipe);

            }
        }
        return recipes;
    }

    /**
     * @param user
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    //Denna ska användas via UserController.
    public void addUser(User user) throws SQLException {
        String userName = user.getUserName();
        String password = user.getPassword();
        String query = "CALL registernewuser(?, ?)";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement statement = connection.prepareCall(query)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("SQLException vid körning av addUser: " + e.getMessage());
            e.printStackTrace();
            throw e;  // Kasta vidare exception så det syns på högre nivå

        }
    }

    /**
     * @param username
     * @return
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public boolean checkUserExists(String username) throws SQLException {
        String query = "{ ? = CALL checkifuserisregistered(?) }";
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
     * @author Christoffer Salomonsson
     * KLAR
     */
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "select * from getallusers()";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement statement = connection.prepareCall(sql)) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("userid"),
                            resultSet.getString("username"),
                            resultSet.getString("userpassword")
                    ));
                }
            }
        }
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
     * @param measurement
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getMeasureId(Measurement measurement) throws SQLException {
        String sql = "select measureid from measures where measure = ?";
        String measure = measurement.toString();
        int measureId = -1;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, measure);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    measureId = resultSet.getInt("measureid");
                }
            }
        }
        return measureId;

    }

    public int getUserID(String userName) throws SQLException {
        String sql = "select userid from users where username = ?";
        int userID = -1;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, userName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    userID = resultSet.getInt("userid");
                }
            }
        }
        return userID;
    }
    public ArrayList<Object> getAllIngredient() throws SQLException{
        String sql = "select ingredientname from ingredients";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            PreparedStatement stmt = connection.prepareCall(sql)){
            try(ResultSet resultSet = stmt.executeQuery()){
                while (resultSet.next()) {
                    String ingredientName = resultSet.getString("ingredientname");
                    Ingredient ingredient = new Ingredient(ingredientName);
                    ingredients.add(ingredient);
                }

            }


        }
        ArrayList<Object> ingredientsToSend = new ArrayList<>(ingredients);
        System.out.println(ingredientsToSend.toString());
        return ingredientsToSend;
    }


}
