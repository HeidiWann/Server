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
    private Measurement measure;
    private Store store;
    private double amountOfIngredient;

    public Ingredient(String ingredientName, double price) {
        this.ingredientName = ingredientName;
        this.price = price;
    }

    public Ingredient(String nameOfIngredient, double costOfIngredient, double amountOfIngredient, Measurement measurement) {
        this.ingredientName = nameOfIngredient;
        this.price = costOfIngredient;
        this.amountOfIngredient = amountOfIngredient;
        this.measure = measurement;
    }

    public Ingredient(int ingredientId, String ingredientName, double price, Measurement measure, Store store, double amountOfIngredient) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.price = price;
        this.measure = measure;
        this.store = store;
        this.amountOfIngredient = amountOfIngredient;
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

    public Measurement getMeasure() {
        return measure;
    }

    public void setMeasure(Measurement measure) {
        this.measure = measure;
    }

    public Store getStore() {
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

    public String toString() {
        return String.format("%s    |    %skr   |   %s%s", ingredientName, price, amountOfIngredient, measure);
    }
}