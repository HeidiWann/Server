package controller;

import model.DatabaseCommunicator;
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
    private ArrayList<Recipe> recipes;
    private List<Recipe> idUser;
    private DatabaseController databaseController;
    private DatabaseCommunicator databaseCommunicator;


    /**
     * Clas constructor
     * @param databaseController
     * @author Anton Jansson
     */
    public RecipeController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }
    /**
     * Method used for fetching recipes from the database
     *
     * @return An ArrayList of recipes
     */
    public ArrayList<Recipe> getNewConnectionInfo() {
        return recipes;
    }
}


