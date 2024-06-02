package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
     * @param databaseCommunicator An instance of {@link DatabaseCommunicator}
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public DatabaseController(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
    }

    /**
     * Takes in a recipe, adds parts of the recipe object to the Food table in the database by passing the recipe name to
     * the method "addFood". Then, the recipe is created with the method "addRecipe". After this we use the method "getFoodID"
     * to get the foodId that is now connected to the recipe in the database. We then add each ingredient in the recipe object
     * to the database through the method "addIngredientToRecipe".
     * Finally, it adds categories to the recipe through the method "addCategoryForRecipe".
     *
     * @param recipe The {@link Recipe} a user wants to add to the database.
     * @author Christoffer Salomonsson
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
            ArrayList<FoodCategory> categories = recipe.getDish().getTypeOfFood();
            for (FoodCategory foodCategory : categories) {
                addCategoryForRecipe(foodCategory, recipe.getRecipeName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes in a recipe object, prepares an SQL query with 5 not determined input parameters for the database.
     * Takes the 5 different parameters needed to create a row in the database for the recipe,
     * then sets these 5 parameters in their correct place in the query to match the database.
     *
     * @param recipe The {@link Recipe} we want to add to the database.
     * @throws SQLException if database error occurs.
     * @author Christoffer Salomonsson
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
     * Takes in the category for the recipe as well as the recipe name. Then uses the methods "getCategoryId" and "getRecipeId"
     * to get the IDs for the recipe and category in the database. It then links these in the recipes categories table.
     *
     * @param foodCategory The {@link FoodCategory} we want to add to a recipe in the database.
     * @param recipeName   The name of the recipe we want to add the food category to.
     * @author Christoffer Salomonsson
     */
    public void addCategoryForRecipe(FoodCategory foodCategory, String recipeName) {
        String sql = "insert into recipescategories (recipeid, categoryid) values (?,?)";
        int categoryid = getCategoryId(foodCategory.toString());
        int recipeID = getRecipeId(recipeName);
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, recipeID);
            stmt.setInt(2, categoryid);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes in the recipename from a recipe object. Then look for matches in the database, if match is found it returns
     * a recipeid in form of a int.
     *
     * @param recipeName The name of the recipe of which we want the id.
     * @return the recipe id of the recipe with the recipe name.
     * @author Christoffer Salomonsson
     */
    public int getRecipeId(String recipeName) {
        String sql = "Select recipeid from recipes where recipename = ?";
        int recipeID = 0;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, recipeName);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                recipeID = resultSet.getInt("recipeid");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipeID;
    }

    /**
     * Takes in a categoryname to look for a match in the database, if match is found it returns a categoryid in form
     * of a int.
     *
     * @param categoryName The category name of the category we want the id from.
     * @return The category id of the category name.
     * @author Christoffer Salomonsson
     */
    public int getCategoryId(String categoryName) {
        String sql = "select categoryid from categories where categoryname = ?";
        int categoryID = 0;
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                categoryID = resultSet.getInt("categoryid");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryID;
    }

    /**
     * Takes in foodname which is the same as recipename from the recipe object. Then adds it to the datebase where the row
     * in table Food also gets a foodid.
     *
     * @param foodName The name of the food we want to add into the database.
     * @throws SQLException if database error occurs.
     * @author Christoffer Salomonsson
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
     * Takes in a string foodname and try to match it to a row in the database table Food. If match is found it returns
     * that rows foodid.
     *
     * @param foodName The food name of the food we want the id from.
     * @return The food id of the food name.
     * @throws SQLException if database error occurs.
     * @author Christoffer Salomonsson
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
     * This method is used for we want to add new ingredients to the table ingredients. It takes in an object of Ingredient.
     * The sql query has 3 non-decided parameters which we set with the variables from the ingredient that we need in the table ingredients
     * in the database.
     *
     * @param ingredient The {@link Ingredient} we want to add to the database.
     * @author Heidi Wännman
     * @author Christoffer Salomonsson
     */
    public void addIngredient(Ingredient ingredient) {
        if (ingredient.getIngredientName() != null && ingredient.getStore() != null) {
            ArrayList<Ingredient> test = getAllIngredient();
            boolean nameExists = false;
            for (Ingredient ingredientFromDataBase : test) {
                if (Objects.equals(ingredientFromDataBase.getIngredientName(), ingredient.getIngredientName())) {
                    nameExists = true;
                    break;
                }
            }
            if (!nameExists) {

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
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            }
        }
    }

    /**
     * Takes in ingredientname from an ingredient object. Searches for match in the database table ingredients. If match
     * is found it returns a ingredientid from the matching row.
     *
     * @param ingredientName The name of the ingredient we want to get the id from.
     * @return The ingredient id of the ingredient name.
     * @throws SQLException if database error occurs.
     * @author Christoffer Salomonsson
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
     * Takes in an Ingredient object and a foodId in form of int. It then calls the procedure "addingredienttofood" which
     * inputs the amount of the ingredient, what type of measurement, ingredientid and foodid.
     *
     * @param ingredient The {@link Ingredient} object we want to add to a recipe in the database.
     * @param foodId     The foodId of the {@link Food} object we want to connect the ingredient to in the database.
     * @throws SQLException if a database error occurs.
     * @author Christoffer Salomonsson
     */
    public void addIngredientToRecipe(Ingredient ingredient, int foodId) throws SQLException {
        String sql = "CALL addingredienttofood(?,?,?,?)";
        int ingredientId = getIngredientId(ingredient.getIngredientName());
        double amountOfIngredient = ingredient.getAmountOfIngredient();
        int measurementid = getMeasureId(ingredient.getMeasure());
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setDouble(1, amountOfIngredient);
            stmt.setInt(2, measurementid);
            stmt.setInt(3, ingredientId);
            stmt.setInt(4, foodId);
            stmt.execute();
        }
    }

    /**
     * This method is used when a client connection is established to the server. It sends all the {@link Recipe}  from the database
     * to the connected client. In the query we select everything that is needed for a recipe that will be used on the client side.
     * After the selects are stated the function "getrecipealldetails" is called. The tables users, food, ingredientsinfood, ingredients,
     * measures, recipescategories, categories are joined to get the right data.
     *
     * @return A {@link ArrayList} with all the {@link Recipe} in the database.
     * @throws SQLException if database error occurs.
     * @author Heidi Wännman
     * @author Christoffer Salomonsson
     */
    public ArrayList<Recipe> getRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT recipename, recipeimage, recipeinstructions, username, foodname, ingredientname, store, ingredientcost, amountofingredient, measure, categoryname FROM getrecipealldetails()";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            Map<String, Recipe> recipeMap = new HashMap<>();
            while (resultSet.next()) {
                String recipeName = resultSet.getString("recipename");
                Recipe recipe = recipeMap.get(recipeName);
                if (recipe == null) {
                    byte[] recipeImage = resultSet.getBytes("recipeimage");
                    String instructions = resultSet.getString("recipeinstructions");
                    String author = resultSet.getString("username");
                    String foodName = resultSet.getString("foodname");
                    String category = resultSet.getString("categoryname");
                    ArrayList<FoodCategory> categories = new ArrayList<>();
                    categories.add(FoodCategory.valueOf(category));
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    recipe = new Recipe(author, instructions, recipeImage, ingredients, foodName, categories);
                    recipeMap.put(recipeName, recipe);
                    recipes.add(recipe);
                }
                String ingredientName = resultSet.getString("ingredientname");
                Store store = Store.valueOf(resultSet.getString("store"));
                double price = resultSet.getDouble("ingredientcost");
                double amount = resultSet.getDouble("amountofingredient");
                Measurement measure = Measurement.valueOf(resultSet.getString("measure"));
                Ingredient ingredient = new Ingredient(ingredientName, store, price, amount, measure);
                recipe.getDish().getIngredients().add(ingredient);

                String category = resultSet.getString("categoryname");
                FoodCategory foodCategory = FoodCategory.valueOf(category);
                if (!recipe.getDish().getTypeOfFood().contains(foodCategory)) {
                    recipe.getDish().getTypeOfFood().add(foodCategory);
                }
            }
        }
        return recipes;
    }

    /**
     * Takes in a user object that is sent indirectly from the client. Calls on the procedure "registernewuser" in the database.
     * Sets the parameters for the procedure with username and password from the user object.
     *
     * @param user The {@link User} object we want to add to the database.
     * @author Heidi Wännman
     * @author Christoffer Salomonsson
     */
    public void addUser(User user) {
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
        }
    }

    /**
     * Method used for fetching users from the database.
     *
     * @return An {@link ArrayList} of all users in the database.
     * @throws SQLException if database error occurs.
     * @author Anton Jansson
     * @author Christoffer Salomonsson
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
     * Method to get measure id of a specific measurement.
     *
     * @param measurement The {@link Measurement} which we want to get the measurement id of.
     * @return The measurement of the specified measurement.
     * @throws SQLException if a database error occurs.
     * @author Christoffer Salomonsson
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

    /**
     * Method to get userId on a user in the database.
     *
     * @param userName The username of the {@link User} that we want the id on.
     * @return the user id of the user or -1 if user id isn't found.
     * @throws SQLException if a database error occurs.
     * @author Christoffer Salomonsson
     */
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

    /**
     * Method to get all ingredients that are currently in the database. It then puts every ingredient in
     * an arraylist that is returned.
     *
     * @return A {@link ArrayList} which has all {@link Ingredient} in the database.
     * @throws SQLException if a database error occurs.
     * @author Christoffer Salomonsson
     */
    public ArrayList<Ingredient> getAllIngredient() {
        String sql = "select ingredientname, ingredientcost, store from ingredients";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareCall(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String ingredientName = resultSet.getString("ingredientname");
                    double cost = resultSet.getDouble("ingredientcost");
                    Store store = Store.valueOf(resultSet.getString("store"));
                    Ingredient ingredient = new Ingredient(ingredientName, cost, store);
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return ingredients;
    }

    /**
     * Adds a users favorite recipe to the database table. The method takes in a user object and a recipe object then
     * uses the id from each object to put it in the database.
     *
     * @param user   The {@link User} which represent the user that wants to add a favorite recipe.
     * @param recipe The{@link Recipe} that the user wants to add to favorites.
     * @author Christoffer Salomonsson
     */
    public void addFavoriteRecipe(User user, Recipe recipe) {
        String sql = "insert into favoriterecipes(userid, recipeid) values(?,?)";
        int userid = user.getUserID();
        int recipeId = recipe.getRecipeID();
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userid);
            stmt.setInt(2, recipeId);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method to get a users own recipes. It takes in a {@link User} object, then takes the userid from said object and put it
     * into the sql question which calls on a database function. A {@link HashMap} is used so that there will only be one {@link Recipe} of each
     * that is created and put into the arraylist which then is returned. First it checks the hashmap to see there isn't already
     * a recipe with the same name, then if there isn't a match it gets all the relevant info towards creating a recipe.
     * Then it goes through the ingredients that is in said recipe and creates an array of them and adds each ingredient
     * to the recipe.food.
     *
     * @param user The{@link User} object representing what user the sql query should look after in favorite recipes.
     * @return An {@link ArrayList} which contains {@link Recipe} of the users favorite recipes.
     * @author Christoffer Salomonsson
     */
    public ArrayList<Recipe> getFavoriteRecipes(User user) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        int userid = user.getUserID();
        String query = "SELECT recipename, recipeimage, recipeinstructions, username, foodname, ingredientname, store, ingredientcost, amountofingredient, measure, categoryname FROM getallfavoriterecipes(?)";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userid);
            try (ResultSet resultSet = stmt.executeQuery()) {
                Map<String, Recipe> recipeMap = new HashMap<>();
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("recipename");
                    Recipe recipe = recipeMap.get(recipeName);
                    if (recipe == null) {
                        byte[] recipeImage = resultSet.getBytes("recipeimage");
                        String instructions = resultSet.getString("recipeinstructions");
                        String author = resultSet.getString("username");
                        String foodName = resultSet.getString("foodname");
                        String category = resultSet.getString("categoryname");
                        ArrayList<FoodCategory> categories = new ArrayList<>();
                        categories.add(FoodCategory.valueOf(category));
                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        recipe = new Recipe(author, instructions, recipeImage, ingredients, foodName, categories);
                        recipeMap.put(recipeName, recipe);
                        recipes.add(recipe);
                    }
                    String ingredientName = resultSet.getString("ingredientname");
                    Store store = Store.valueOf(resultSet.getString("store"));
                    double price = resultSet.getDouble("ingredientcost");
                    double amount = resultSet.getDouble("amountofingredient");
                    Measurement measure = Measurement.valueOf(resultSet.getString("measure"));
                    Ingredient ingredient = new Ingredient(ingredientName, store, price, amount, measure);
                    recipe.getDish().getIngredients().add(ingredient);
                    String category = resultSet.getString("categoryname");
                    FoodCategory foodCategory = FoodCategory.valueOf(category);
                    if (!recipe.getDish().getTypeOfFood().contains(foodCategory)) {
                        recipe.getDish().getTypeOfFood().add(foodCategory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }

    /**
     * A method to get a users own recipes. It takes in a {@link User} object, then takes the userid from said object and put it
     * into the sql question which calls on a database function. A {@link HashMap} is used so that there will only be one {@link Recipe} of each
     * that is created and put into the arraylist which then is returned. First it checks the hashmap to see there isn't already
     * a recipe with the same name, then if there isn't a match it gets all the relevant info towards creating a recipe.
     * Then it goes through the ingredients that is in said recipe and creates an array of them and adds each ingredient
     * to the recipe.food.
     *
     * @param user The{@link User} object representing the user to be able to search for created recipes that the user is author to.
     * @return An {@link ArrayList} which contains {@link Recipe} of the users created recipes.
     * @author Christoffer Salomonsson
     */
    public ArrayList<Recipe> getOwnRecipes(User user) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        int userid = user.getUserID();
        String query = "SELECT recipename, recipeimage, recipeinstructions, username, foodname, ingredientname, store, ingredientcost, amountofingredient, measure, categoryname FROM getallownrecipes(?)";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userid);
            try (ResultSet resultSet = stmt.executeQuery()) {
                Map<String, Recipe> recipeMap = new HashMap<>();
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("recipename");
                    Recipe recipe = recipeMap.get(recipeName);
                    if (recipe == null) {
                        byte[] recipeImage = resultSet.getBytes("recipeimage");
                        String instructions = resultSet.getString("recipeinstructions");
                        String author = resultSet.getString("username");
                        String foodName = resultSet.getString("foodname");
                        String category = resultSet.getString("categoryname");
                        ArrayList<FoodCategory> categories = new ArrayList<>();
                        categories.add(FoodCategory.valueOf(category));
                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        recipe = new Recipe(author, instructions, recipeImage, ingredients, foodName, categories);
                        recipeMap.put(recipeName, recipe);
                        recipes.add(recipe);
                    }
                    String ingredientName = resultSet.getString("ingredientname");
                    Store store = Store.valueOf(resultSet.getString("store"));
                    double price = resultSet.getDouble("ingredientcost");
                    double amount = resultSet.getDouble("amountofingredient");
                    Measurement measure = Measurement.valueOf(resultSet.getString("measure"));
                    Ingredient ingredient = new Ingredient(ingredientName, store, price, amount, measure);
                    recipe.getDish().getIngredients().add(ingredient);
                    String category = resultSet.getString("categoryname");
                    FoodCategory foodCategory = FoodCategory.valueOf(category);
                    if (!recipe.getDish().getTypeOfFood().contains(foodCategory)) {
                        recipe.getDish().getTypeOfFood().add(foodCategory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
}