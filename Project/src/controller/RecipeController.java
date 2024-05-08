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
 * @author Anton Persson
 */
public class RecipeController {
    private DatabaseController databaseController;
    private ArrayList<Recipe> recipes;

    /**
     * Clas constructor
     *
     * @param databaseController
     * @author Anton Jansson
     */
    public RecipeController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void setRecipes(ArrayList<Recipe> recipe) {
        this.recipes = recipe;
    }

    public ArrayList<Recipe> getRecipe() {
        return recipes;
    }
}


