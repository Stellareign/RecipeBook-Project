package me.ruana.recipeBook.dto;

import me.ruana.recipeBook.model.Ingredients;

import java.util.Objects;

public class IngredientsDTO {
    private final String ingredientName;
    private final int numberOfIngredients;
    private final String measureUnits;
    private final int id;

    public IngredientsDTO (int id, String ingredientName, int numberOfIngredients, String measureUnits) {
        this.ingredientName = ingredientName;
        this.numberOfIngredients = numberOfIngredients;
        this.measureUnits = measureUnits;
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public String getMeasureUnits() {
        return measureUnits;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientsDTO that)) return false;
        return getNumberOfIngredients() == that.getNumberOfIngredients() && getId() == that.getId() && getIngredientName().equals(that.getIngredientName()) && getMeasureUnits().equals(that.getMeasureUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientName(), getNumberOfIngredients(), getMeasureUnits(), getId());
    }
    public static IngredientsDTO from(int id, Ingredients ingredients){
        return new IngredientsDTO(id, ingredients.getIngredientName(), ingredients.getNumberOfIngredients(), ingredients.getMeasureUnits());
    }

    @Override
    public String toString() {
        return  id + ". " + "Наименование: " + ingredientName + '\'' +
                "\n Количество " + numberOfIngredients +
                " " + measureUnits;
    }
}
