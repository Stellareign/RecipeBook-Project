package me.ruana.recipeBook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingredients {
    private String ingredientName;
    private double numberOfIngredients;
    private String measureUnits;
}
