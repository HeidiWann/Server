package model;

import java.util.ArrayList;

public class Food {
    private String nameOfFood;
    private int foodID;

    private ArrayList<FoodCategory> typeOfFood;
    private double costOfFood;
    private ArrayList<Ingredient> ingredients;

    public Food(String nameOfFood, ArrayList<FoodCategory> typeOfFood, ArrayList<Ingredient> ingredients) {
        this.nameOfFood = nameOfFood;
        this.typeOfFood = typeOfFood;
        this.ingredients = ingredients;
        double totalCost = 0;
        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                totalCost += ingredient.getPrice();
            }
        }
        this.costOfFood = totalCost;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCostOfFood() {
        return costOfFood;
    }

    public void setCostOfFood(int costOfFood) {
        this.costOfFood = costOfFood;
    }

    public ArrayList<FoodCategory> getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(ArrayList<FoodCategory> typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }
}
