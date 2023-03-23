package me.ruana.recipeBook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.ruana.recipeBook.model.Ingredients;

@Data
@AllArgsConstructor
public class IngredientsDTO {
    private final int id;
    private final String ingredientName;
    private final double numberOfIngredients;
    private final String measureUnits;

    // ============== СТАТИЧЕСКАЯ ФАБРИКА СБОРКА DTO: ==============
    public static IngredientsDTO from(int id, Ingredients ingredients) {
        return new IngredientsDTO(id, ingredients.getIngredientName(), ingredients.getNumberOfIngredients(), ingredients.getMeasureUnits());
    }

}
