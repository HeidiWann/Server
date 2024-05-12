package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
     *
     * @param recipe
     * @param ingredients
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void userAddRecipe(Recipe recipe, ArrayList<Ingredient> ingredients){
        try {
            addFood(recipe.getRecipeName());
            addRecipe(recipe);
            int foodId = getFoodId(recipe.getRecipeName());
            for( Ingredient ingredient: ingredients){
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
            stmt.setInt(5,foodId);
            stmt.execute();
        }
    }

    /**
     *
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
        int authorId = recipe.getAuthor();
        int foodId = getFoodId(recipe.getRecipeName());
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, recipeName);
            stmt.setBytes(2, recipeImage);
            stmt.setString(3, recipeInstructions);
            stmt.setInt(4, authorId);
            stmt.setInt(5,foodId);
            stmt.execute();
        }
    }

    /**
     *
     * @param foodName
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addFood(String foodName) throws SQLException{
        String sql = "CALL insertnewfood(?)";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             CallableStatement stmt = connection.prepareCall(sql)){
            stmt.setString(1,foodName);
            stmt.execute();
        }
    }

    /**
     *
     * @param foodName
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getFoodId(String foodName) throws SQLException{
        String sql = "SELECT foodid FROM food where foodname = ?";
        int foodId = -1;
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            PreparedStatement stmt = connection.prepareCall(sql)){
            stmt.setString(1, foodName);
            try(ResultSet resultSet = stmt.executeQuery()){
                if(resultSet.next()){
                    foodId = resultSet.getInt("foodid");
                }
            }
        }
        return foodId;
    }

    /**
     *
     * @param ingredient
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addIngredient(Ingredient ingredient) throws SQLException{
        String sql = "CALL insertingredient(?,?,?)";
        String nameOfIngredient = ingredient.getIngredientName();
        Store nameOfStore = ingredient.getStore();
        Double costOfIngredient = ingredient.getIngredientCost();
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            CallableStatement stmt = connection.prepareCall(sql)){
            stmt.setString(1,nameOfIngredient);
            stmt.setString(2,nameOfStore.toString());
            stmt.setDouble(3,costOfIngredient);
            stmt.execute();
        }
    }

    /**
     *
     * @param ingredientName
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getIngredientId(String ingredientName) throws SQLException{
        String sql = "Select ingredientid FROM ingredients where ingredientname=?";
        int ingredientId = -1;
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            PreparedStatement stmt = connection.prepareCall(sql)){
            stmt.setString(1, ingredientName);
            try(ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    ingredientId = resultSet.getInt("ingredientid");
                }
            }
        }
        return ingredientId;
    }

    /**
     *
     * @param ingredient
     * @param foodId
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public void addIngredientToRecipe(Ingredient ingredient, int foodId) throws SQLException{
        String sql = "CALL addingredienttofood(?,?,?,?)";
        int ingredientId = getIngredientId(ingredient.getIngredientName());
        double amountOfIngredient = ingredient.getAmountOfIngredient();
        int measurementid = getMeasureId(ingredient.getMeasure());
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            CallableStatement stmt = connection.prepareCall(sql)){
            stmt.setDouble(1,amountOfIngredient);
            stmt.setInt(2, measurementid);//Måste ändras
            stmt.setInt(3, ingredientId);
            stmt.setInt(4, foodId);
            stmt.execute();
        }
    }

    /**
     *
     * @return
     * @throws SQLException
     * @author Anton Persson
     * @author Christoffer Salomonsson
     * KLAR
     */
    public ArrayList<Recipe> getRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";
        try (Connection connection = databaseCommunicator.getDatabaseconnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                ImageView imageView = byteArrayToImageView(resultSet.getBytes("recipeimage"));
                recipes.add(new Recipe(
                        resultSet.getInt("recipeid"),
                        resultSet.getString("recipename"),
                        imageView,
                        resultSet.getString("recipeinstructions"),
                        resultSet.getInt("authorid")
                ));
            }
        }
        return recipes;
    }

    /**
     *
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
        }
    }

    /**
     *
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
     *
     * @param measurement
     * @return
     * @throws SQLException
     * @author Christoffer Salomonsson
     * KLAR
     */
    public int getMeasureId(Measurement measurement) throws SQLException{
        String sql = "select measureid from measures where measure = ?";
        String measure = measurement.toString();
        int measureId = -1;
        try(Connection connection = databaseCommunicator.getDatabaseconnection();
            PreparedStatement stmt = connection.prepareCall(sql)){
            stmt.setString(1, measure);
            try(ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    measureId = resultSet.getInt("measureid");
                }
            }
        }
        return measureId;

    }

    /*
    Dessa två sista metoder är till för att konvertera mellan byte och imageview
     */
    public static byte[] imageViewToByteArray(ImageView imageView) {
        Image image = imageView.getImage();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", byteOutput);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fel vid konvertering till byte[]");
        }
        return byteOutput.toByteArray();
    }
    public static ImageView byteArrayToImageView(byte[] byteArray) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
