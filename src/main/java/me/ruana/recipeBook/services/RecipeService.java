package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {
    private static int recipeId = 0;
    private final Map<Integer, Recipe> recipeMap = new HashMap<>();

    // ДОБАВЛЕНИЕ РЕЦЕТА:
    public RecipeDTO addRecipeToMap(Recipe recipe) { // добавление рецепта в мапу
        int id = recipeId++;
        recipeMap.put(id, recipe);
        return RecipeDTO.from(id, recipe); // возвращаем DTO из стат.фабрики
    }

    // ПОЛУЧЕНИЕ РЕЦЕПТА:
    public RecipeDTO getRecipe(int recipeId) { // извлекаем нужный рецепт
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null && recipeMap.containsKey(recipeId)) {
            return RecipeDTO.from(recipeId, recipe);
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
    }

    // РЕДАКТИРОВАНИЕ РЕЦЕПТА:
    public RecipeDTO editRecipe(int recipeId, Recipe newRecipe) { // извлекаем нужный рецепт
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null) {
            recipe.setTitle(newRecipe.getTitle());
            recipe.setCookingTime(newRecipe.getCookingTime());
            recipe.setMeasureTime(newRecipe.getMeasureTime());
            recipe.setIngredientsList(newRecipe.getIngredientsList());
            recipe.setCookingStepsList(newRecipe.getCookingStepsList());

            return RecipeDTO.from(recipeId, recipe);
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
    }

    // УДАЛЕНИЕ РЕЦЕПТА:
    public Map<Integer, Recipe> deleteRecipe(int recipeId) {
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null) {
            recipeMap.remove(recipeId);
            //  System.out.println("Рецепт " + recipeId + " " + recipe.getTitle() + " удалён из книги рецептов.");
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
        return recipeMap;
    }

    // ПОЛУЧЕНИЕ СПИСКА РЕЦЕПТОВ:
    public Map<Integer, Recipe> getRecipeMap() {
        return recipeMap;
    }
}
