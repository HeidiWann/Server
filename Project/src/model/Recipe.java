package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Clas that represents recipes
 *
 * @author Heidi Wännman
 */
public class Recipe implements Serializable {
    @Serial
    private static final long serialVersionUID = 111222333L;
    private int id;
    private String recipeTitle;
    private byte[] recipeImage;
    private String recipeInstructions;
    private int authorId;

    public Recipe(int recipeID, String title, byte[] image, String instructions) {
        this.id = recipeID;
        this.recipeTitle = title;
        this.recipeImage = image;
        this.recipeInstructions = instructions;
    }
    public Recipe(String title, byte[] image, String instructions, int authorId){
        this.recipeTitle = title;
        this.recipeImage = image;
        this.recipeInstructions = instructions;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public byte[] getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(byte[] recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
