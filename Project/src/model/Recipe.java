package model;





import javafx.scene.image.ImageView;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable { // --------------------------------------------------------------------------------Klassen har ingen author
    @Serial
    private static final long serialVersionUID = 111222333L;
    private int recipeID;
    private int author;
    private String instructions;
    private byte[] imageOfRecipe;
    private Food dish;
    private String recipeName;

    public Recipe(int author, String recipeName, String instructions, byte[] imageOfRecipe, ArrayList<Ingredient> ingredients, String nameOfFood, ArrayList<FoodCategory> typeOfFood) {
        Food newDish = new Food(nameOfFood, typeOfFood, ingredients);
        this.author = author;
        this.dish = newDish;
        this.instructions = instructions;
        this.imageOfRecipe = imageOfRecipe;
        this.recipeName = nameOfFood;
    }

    public Recipe(int recipeid, String recipename, ImageView imageView, String recipeinstructions, int authorid) {
    }
    /*public Recipe(int author, String recipeName, String instructions, ImageView imageOfRecipe, ArrayList<Ingredient> ingredients, String nameOfFood, ArrayList<FoodCategory> typeOfFood) {
        Food newDish = new Food(nameOfFood, typeOfFood, ingredients);
        this.author = author;
        this.dish = newDish;
        this.instructions = instructions;
        this.imageOfRecipe = imageOfRecipe;
        this.recipeName = nameOfFood;
    }

     */

    /*public Recipe(int recipeID, int author, String instructions, ImageView imageOfRecipe, ArrayList<Ingredient> ingredients, String nameOfFood, ArrayList<FoodCategory> typeOfFood) {
        Food newDish = new Food(nameOfFood, typeOfFood, ingredients);
        this.recipeID = recipeID;
        this.author = author;
        this.dish = newDish;
        this.instructions = instructions;
        this.imageOfRecipe = imageOfRecipe;
        this.recipeName = nameOfFood;
    }

    public Recipe(int recipeid, String recipename, ImageView recipeimages, String recipeinstructions, int authorid) {
        this.recipeID = recipeid;
        this.recipeName = recipename;
        this.imageOfRecipe = recipeimages;
        this.instructions = recipeinstructions;
        this.author = authorid;
    }

     */


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public byte[] getImageOfRecipe() {
        return imageOfRecipe;
    }

    //public void setImageOfRecipe(ImageView imageOfRecipe) {
      //  this.imageOfRecipe = imageOfRecipe;
   // }

    public Food getDish() {
        return dish;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getAuthor() {
        return author;
    }
}
