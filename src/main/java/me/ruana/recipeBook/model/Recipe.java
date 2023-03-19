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
}

