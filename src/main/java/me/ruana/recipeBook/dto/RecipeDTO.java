package me.ruana.recipeBook.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.model.Recipe;

import java.util.List;
@Data
@AllArgsConstructor
public class RecipeDTO { // файл типа json
    private final int id;
    private final String title;
    private String numberOfServings;
    private final double cookingTime;
    private final String measureTime;
    private final List<Ingredients> ingredientsList;
    private final List<String> cookingStepsList;



//    public RecipeDTO(int id, String title, int cookingTime, String measureTime, List<Ingredients> ingredientsList, List<String> cookingStepsList) {
//        this.id = id;
//        this.title = title;
//        this.cookingTime = cookingTime;
//        this.measureTime = measureTime;
//        this.ingredientsList = ingredientsList;
//        this.cookingStepsList = cookingStepsList;
//
//    }

//    public String getTitle() {
//        return title;
//    }
//
//    public int getCookingTime() {
//        return cookingTime;
//    }
//
//    public List<Ingredients> getIngredientsList() {
//        return ingredientsList;
//    }
//
//    public List<String> getCookingStepsList() {
//        return cookingStepsList;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getMeasureTime() {
//        return measureTime;
//    }
//
//
    public static RecipeDTO from(int id, Recipe recipe) { // "статическая фабрика - создание DTO из заданных "компонентов"
        return new RecipeDTO(id, recipe.getTitle(), recipe.getNumberOfServings(), recipe.getCookingTime(), recipe.getMeasureTime(), recipe.getIngredientsList(),
                recipe.getCookingStepsList());
    }

//    @Override
//    public String toString() {
//        return id + "." + "Рецепт приготовления: " +
//                title + "." +
//                "\n Время приготовления" + cookingTime + " " + measureTime +
//                "\n Ингредиенты: " + ingredientsList +
//                "Шаги приготовления: " + cookingStepsList;
//    }
}
