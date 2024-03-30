package model;

public class Ingredient {
    private int id;
    private String ingredientName;
    private double price;
    private String measure;
    public Ingredient( String ingredientName, double price) {

        this.ingredientName = ingredientName;
        this.price = price;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
