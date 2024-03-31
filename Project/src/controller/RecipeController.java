package controller;

import model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Clas responsible for handling logic related to recipes
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class RecipeController {
    private Recipe recipe;
    private List<Recipe> idUser;
    private DatabaseController dbController;


    /**
     * Clas constructor
     * @param dbController
     * @author Anton Jansson
     */
    public RecipeController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    /**
     * @author Heidi Wännman
     */
    public void createRecipe() {
        String createRecipeQuery = "";
        dbController.executeQueryVoidReturn(createRecipeQuery);
    }

    /**
     * @author Heidi Wännman
     */
    public void updateRecipe() {
        String updateRecipeQuery = "";
        dbController.executeQueryVoidReturn(updateRecipeQuery);
    }

    /**
     * @author Heidi Wännman
     */
    public void deleteRecipe() {
        String deleteRecipeQuery = "";
        dbController.executeQueryVoidReturn(deleteRecipeQuery);
    }

    /**
     * Method used for fetching recipes from the database
     *
     * @return An ArrayList of recipes
     */
    public ArrayList<Recipe> getNewConnectionInfo() {
        return dbController.getRecipesForNewConnection();
    }
}


