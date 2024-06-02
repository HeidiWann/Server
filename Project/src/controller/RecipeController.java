package controller;

import model.Ingredient;
import model.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class responsible for handling logic related to recipes
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 * @author Anton Persson
 */
public class RecipeController {
    private DatabaseController databaseController;
    private ArrayList<Recipe> recipes;

    /**
     * Class constructor
     *
     * @param databaseController An instance of {@link DatabaseController}
     * @author Anton Jansson
     */
    public RecipeController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void createRecipe(Recipe recipe)  {
        databaseController.userAddRecipe(recipe);
    }

    public void setRecipes(ArrayList<Recipe> recipe) {
        this.recipes = recipe;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void updateAllRecipes() throws SQLException {
        this.recipes = databaseController.getRecipes();
    }

    public ArrayList<Object> getIngredientsStartup() throws SQLException {
        ArrayList<Ingredient> ingredients = databaseController.getAllIngredient();
        ArrayList<Object> ingredientsStartup = new ArrayList<>(ingredients);
        return ingredientsStartup;
    }

}


