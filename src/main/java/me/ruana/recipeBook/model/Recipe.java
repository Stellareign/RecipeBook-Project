package me.ruana.recipeBook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String title;
    private String numberOfServings;
    private double cookingTime;
    private String measureTime;
    private List<Ingredients> ingredientsList;
    private List<String> cookingStepsList;


//    @JsonCreator // для запуска метода чтения из файла через десериализацию json в мапу
//    public Recipe(@JsonProperty("title") String title, @JsonProperty("numberOfServings") String numberOfServings,
//                  @JsonProperty("cookingTime") double cookingTime, @JsonProperty("measureTime") String measureTime,
//                  @JsonProperty("ingredientsList") List<Ingredients> ingredientsList, @JsonProperty("cookingStepsList")
//                  List<String> cookingStepsList) {
//        this.title = title;
//        this.numberOfServings = numberOfServings;
//        this.cookingTime = cookingTime;
//        this.measureTime = measureTime;
//        this.ingredientsList = ingredientsList;
//        this.cookingStepsList = cookingStepsList;
//    }


//    public Recipe(String title, int cookingTime,String measureTime, List<Ingredients> ingredientsList, List<String> cookingStepsList) {
//        this.title = title;
//        this.cookingTime = cookingTime;
//        this.measureTime = measureTime;
//        this.ingredientsList = ingredientsList;
//        this.cookingStepsList = cookingStepsList;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public Recipe setTitle(String title) {
//        this.title = title;
//        return this;
//    }
//
//    public int getCookingTime() {
//        return cookingTime;
//    }
//
//    public Recipe setCookingTime(int cookingTime) {
//        this.cookingTime = cookingTime;
//        return this;
//    }
//
//    public List<Ingredients> getIngredientsList() {
//        return ingredientsList;
//    }
//
//    public Recipe setIngredientsList(List<Ingredients> ingredientsList) {
//        this.ingredientsList = ingredientsList;
//        return this;
//    }
//
//    public List<String> getCookingStepsList() {
//        return cookingStepsList;
//    }
//
//    public Recipe setCookingStepsList(List<String> cookingStepsList) {
//        this.cookingStepsList = cookingStepsList;
//        return this;
//    }
//
//    public String getMeasureTime() {
//        return measureTime;
//    }
//
//    public Recipe setMeasureTime(String measureTime) {
//        this.measureTime = measureTime;
//        return this;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Recipe recipe)) return false;
//        return getCookingTime() == recipe.getCookingTime() && getTitle().equals(recipe.getTitle()) && getIngredientsList().equals(recipe.getIngredientsList()) && getCookingStepsList().equals(recipe.getCookingStepsList());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getTitle(), getCookingTime(), getMeasureTime(), getIngredientsList(), getCookingStepsList());
//    }
//
//    @Override
//    public String toString() {
//        return "Рецепт приготовления " + title +
//                "\n Время приготовления: " + cookingTime +
//                " " + measureTime + '\'' +
//                "\n Ингредиенты: " + ingredientsList +
//                "\n Шаги приготовления: " + cookingStepsList;
//    }


}

