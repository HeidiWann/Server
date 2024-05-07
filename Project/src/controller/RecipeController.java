package controller;

import view.ClientConnection;
import model.DatabaseCommunicator;
import model.Recipe;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clas responsible for handling logic related to recipes
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class RecipeController {
    private DatabaseController databaseController;
    private DatabaseCommunicator databaseCommunicator;
    private HashMap<Recipe, ClientConnection> recipeReceved = new HashMap<>();


    /**
     * Clas constructor
     * @param databaseCommunicator
     * @author Anton Jansson
     */
    public RecipeController(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
    }
    public void sendRecipe(ObjectOutputStream oos, Recipe recipe) {
        try {
            if (recipe != null) {
                oos.writeObject(recipe);
                oos.flush();
                System.out.println("The recipe has been sent successfully");
            } else {
                System.out.println("There was no recipe to send");
            }
        } catch (IOException e) {
            System.out.println("Could not send recipe " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Recipe getRecipeFromObject(Object object) {

        if (object instanceof Recipe) {
            return (Recipe) object;
        }
        System.out.println("Received object is not a user");
        return null;
    }

    public  HashMap<Recipe, ClientConnection> getNewRecipeInfo() throws SQLException {

        HashMap<Recipe, ClientConnection> recipes = new HashMap<>();
        List<Recipe> recipesList = databaseController.getAllRecipes();

        for (Recipe recipe : recipesList) {
            recipes.put(recipe, null);
        }
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipe) {

    }
    public void handleRecipe(Object object) {

        Recipe recipe = (Recipe) object;
    }
    public void updateRecipe(Recipe recipe) {

    }
    public Recipe getRecipe(Recipe recipe) {

        return recipe;
    }


}


