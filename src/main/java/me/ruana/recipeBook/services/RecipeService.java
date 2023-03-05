package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {
    private static int recipeId = 0;
    private final  Map<Integer, Recipe> recipeMap = new HashMap<>();

    public RecipeDTO addRecipeToMap(Recipe recipe) { // добавление рецепта в мапу
        int id = recipeId++;
        recipeMap.put(id, recipe);
        return RecipeDTO.from(id, recipe); // возвращаем DTO из стат.фабрики
    }


    public  RecipeDTO getRecipe(int recipeId) { // извлекаем нужный рецепт
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null && recipeMap.containsKey(recipeId)) {
            return RecipeDTO.from(recipeId, recipe);
        } else throw new IllegalArgumentException ("Рецепта с таким ID нет!");
    }

}
