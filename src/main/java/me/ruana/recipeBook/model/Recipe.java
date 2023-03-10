package me.ruana.recipeBook.model;


import java.util.List;
import java.util.Objects;

public class Recipe {
    private String title;
    private int cookingTime;
    private  String measureTime;
    private  List<Ingredients> ingredientsList;
    private  List<String> cookingStepsList;

    public Recipe(String title, int cookingTime,String measureTime, List<Ingredients> ingredientsList, List<String> cookingStepsList) {
        this.title = title;
        this.cookingTime = cookingTime;
        this.measureTime = measureTime;
        this.ingredientsList = ingredientsList;
        this.cookingStepsList = cookingStepsList;
    }

    public String getTitle() {
        return title;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public Recipe setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public Recipe setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
        return this;
    }

    public List<String> getCookingStepsList() {
        return cookingStepsList;
    }

    public Recipe setCookingStepsList(List<String> cookingStepsList) {
        this.cookingStepsList = cookingStepsList;
        return this;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public Recipe setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe recipe)) return false;
        return getCookingTime() == recipe.getCookingTime() && getTitle().equals(recipe.getTitle()) && getIngredientsList().equals(recipe.getIngredientsList()) && getCookingStepsList().equals(recipe.getCookingStepsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getCookingTime(), getMeasureTime(), getIngredientsList(), getCookingStepsList());
    }

    @Override
    public String toString() {
        return "Рецепт приготовления " + title +
                "\n Время приготовления: " + cookingTime +
                " " + measureTime + '\'' +
                "\n Ингредиенты: " + ingredientsList +
                "\n Шаги приготовления: " + cookingStepsList;
    }


}
