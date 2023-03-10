package me.ruana.recipeBook.model;

import java.util.Objects;

public class Ingredients {
    private String ingredientName;
    private double numberOfIngredients;
    private String measureUnits;

    public Ingredients(String ingredientName, double numberOfIngredients, String measureUnits) {
        this.ingredientName = ingredientName;
        this.numberOfIngredients = numberOfIngredients;
        this.measureUnits = measureUnits;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public String getMeasureUnits() {
        return measureUnits;
    }

    public Ingredients setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
        return this;
    }

    public Ingredients setNumberOfIngredients(double numberOfIngredients) {
        this.numberOfIngredients = numberOfIngredients;
        return this;
    }

    public Ingredients setMeasureUnits(String measureUnits) {
        this.measureUnits = measureUnits;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredients that)) return false;
        return getNumberOfIngredients() == that.getNumberOfIngredients() && getIngredientName().equals(that.getIngredientName()) && getMeasureUnits().equals(that.getMeasureUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientName(), getNumberOfIngredients(), getMeasureUnits());
    }

    @Override
    public String toString() {
        return "\n Наименование: " + ingredientName +
                "\n Количество: " + numberOfIngredients + " " + measureUnits;
    }
}
