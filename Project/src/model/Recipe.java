package model;


import javafx.scene.image.ImageView;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable { // --------------------------------------------------------------------------------Klassen har ingen author
    @Serial
    private static final long serialVersionUID = 111222333L;
    private int recipeID;
    private String author;
    private String instructions;
    private ImageView imageOfRecipe;
    private Food dish;
    private String recipeName;

    public Recipe(String author, String instructions, ImageView imageOfRecipe, ArrayList<Ingredient> ingredients, String nameOfFood, ArrayList<FoodCategory> typeOfFood) {
        Food newDish = new Food(nameOfFood, typeOfFood, ingredients);
        this.author = author;
        this.dish = newDish;
        this.instructions = instructions;
        this.imageOfRecipe = imageOfRecipe;
        this.recipeName = nameOfFood;
    }

    public Recipe(int recipeID, String author, String instructions, ImageView imageOfRecipe, ArrayList<Ingredient> ingredients, String nameOfFood, ArrayList<FoodCategory> typeOfFood) {
        Food newDish = new Food(nameOfFood, typeOfFood, ingredients);
        this.recipeID = recipeID;
        this.author = author;
        this.dish = newDish;
        this.instructions = instructions;
        this.imageOfRecipe = imageOfRecipe;
        this.recipeName = nameOfFood;
    }


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ImageView getImageOfRecipe() {
        return imageOfRecipe;
    }

    public void setImageOfRecipe(ImageView imageOfRecipe) {
        this.imageOfRecipe = imageOfRecipe;
    }

    public Food getDish() {
        return dish;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
