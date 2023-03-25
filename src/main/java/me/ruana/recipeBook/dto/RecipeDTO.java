package me.ruana.recipeBook.dto;

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
    private final String numberOfServings;
    private final double cookingTime;
    private final String measureTime;
    private final List<Ingredients> ingredientsList;
    private final List<String> cookingStepsList;

    // ============== СТАТИЧЕСКАЯ ФАБРИКА СБОРКА DTO: ==============
    public static RecipeDTO from(int id, Recipe recipe) { // "статическая фабрика - создание DTO из заданных "компонентов"
        return new RecipeDTO(id, recipe.getTitle(), recipe.getNumberOfServings(), recipe.getCookingTime(), recipe.getMeasureTime(), recipe.getIngredientsList(),
                recipe.getCookingStepsList());
    }
}