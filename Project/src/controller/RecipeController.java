package controller;

import model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeController {
    private Recipe recipe;
    private List<Recipe> idUser;
    private DatabaseController dbController;

    public RecipeController(DatabaseController dbController) {
        this.dbController = dbController;


    }
    public void createRecipe() {
    String createRecipeQuery = "";
    dbController.executeQuery(createRecipeQuery);
    }
    public void updateRecipe() {
        String updateRecipeQuery="";
        dbController.executeQuery(updateRecipeQuery);
    }
    public void deleteRecipe() {
        String deleteRecipeQuery="";
        dbController.executeQuery(deleteRecipeQuery);
    }

    public ArrayList<Recipe> getNewConnectionInfo() {
        return dbController.getRecipesForNewConnection();
    }
}


