package model;

import java.awt.*;

/**
 * Clas that represents recipes
 *
 * @author Heidi Wännman
 */
public class Recipe {
    private int id;
    private String title;
    private Image RecipeImage;

    private Ingredient ingredient;
    private User user;
    private int idUser;
    public Recipe() {
    }



    public Recipe(int recipeID, String recipeName, byte[] recipeImages, String recipeInstructions) { //orkar inte fixa nu men ska sätta mig och fixa till alla mina quick fixes
    }

    public Recipe(int foodID, String foodName) {
    }

    public void addIngredient() {
    }
    public void removeIngredient() {
    }
    public void editIngredient() {
    }

    public String getRecipeName() {
        return getRecipeName();
    }

    public byte[] getRecipeImage() {
        return recipeImage();
    }

    private byte[] recipeImage() {
        return null;
    }

    public String getRecipeInstructions() {
        String recipeInstructions = "";
        return recipeInstructions;
    }

    public int getRecipeID() {
        int recipeID = 0;
        return recipeID;
    }

    public String getFoodName() {
        String foodName = "";
        return foodName;
    }

    public int getFoodID() {
        int foodID = 0;
        return foodID;
    }

    public int getUserID() {
        int userID = 0;
        return userID;
    }
}
