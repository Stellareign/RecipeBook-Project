package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;

import java.util.Map;

public interface IngredientsService {
    // ДОБАВЛЯЕМ ИНГРДИЕНТ В МАПУ:
    IngredientsDTO addIngredientToMap(Ingredients ingredient);

    // ИЗВЛЕКАЕМ ИНГРЕДИЕНТ (по аналогии с рецептами):
    IngredientsDTO getIngredient(int ingredientId);

    // ВСЕ ИНГРЕДИЕНТЫ:
    Map<Integer, Ingredients> getIngredientsMap();

    // РЕДАКТИРОВАНИЕ ИНГРЕДИЕНТА:
    IngredientsDTO editIngredient(int ingredientId, Ingredients newIngredient);

    // УДАЛЕНИЕ ИНГРЕДИЕНТА:
    Map<Integer, Ingredients> deleteIngredient(int ingredientId);
}
