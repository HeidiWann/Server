package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Clas that represents ingredients
 *
 * @author Heidi Wännman
 */
public class Ingredient implements Serializable {
    @Serial
    private static final long serialVersionUID = 333444555L;
    private int ingredientId;
    private String ingredientName;
    private double price;
    private String measure;
    private String store; //Kan vara att det är bättre att skapa en affärklass

    public Ingredient(String ingredientName, double price) {

        this.ingredientName = ingredientName;
        this.price = price;


    }
    public Ingredient(int measureID, String measure) {
    }
    public int getId() {
        return ingredientId;
    }
    public void setId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    public String getIngredientName() {
        return ingredientName;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getMeasure() {
        return measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }
    public String getStore() {
        return store;
    }
    public double getIngredientCost() {

        return price;
    }
    public int getIngredientID() {
        return ingredientId;
    }
    public int getMeasureID() {
        int measureID = 0;
        return measureID;
    }
}
