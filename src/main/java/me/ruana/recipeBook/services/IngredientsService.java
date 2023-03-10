package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsService {
    private static int ingredientId = 0;
    private final Map<Integer, Ingredients> ingredientsMap = new HashMap<>();

    // ДОБАВЛЯЕМ ИНГРДИЕНТ В МАПУ:
    public IngredientsDTO addIngredientToMap(Ingredients ingredient) {
        int id = ingredientId++;
        ingredientsMap.put(id, ingredient);
        return IngredientsDTO.from(id, ingredient); // возвращаем DTO из стат.фабрики
    }

    // ИЗВЛЕКАЕМ ИНГРЕДИЕНТ (по аналогии с рецептами):
    public IngredientsDTO getIngredient(int ingredientId) {
        Ingredients ingredients = ingredientsMap.get(ingredientId);
        if (ingredients != null) {
            return IngredientsDTO.from(ingredientId, ingredients);
        } else throw new IllegalArgumentException("Ингредиент отсутствует в списке");
    }

    // ВСЕ ИНГРЕДИЕНТЫ:
    public Map<Integer, Ingredients> getIngredientsMap() {
        return ingredientsMap;
    }

    // РЕДАКТИРОВАНИЕ ИНГРЕДИЕНТА:
    public IngredientsDTO editIngredient(int ingredientId, Ingredients newIngredient) {
        Ingredients ingredient = ingredientsMap.get(ingredientId);
        if (ingredient != null) {
            ingredient.setIngredientName(newIngredient.getIngredientName());
            ingredient.setNumberOfIngredients(newIngredient.getNumberOfIngredients());
            ingredient.setMeasureUnits(newIngredient.getMeasureUnits());
            return IngredientsDTO.from(ingredientId, ingredient); // возвращаем DTO, состоящий из id и тела ингредиента
        } else throw new IllegalArgumentException("Ингредиента с таким ID нет!");
    }

    // УДАЛЕНИЕ ИНГРЕДИЕНТА:
    public Map<Integer, Ingredients> deleteIngredient(int ingredientId) {
        Ingredients ingredient = ingredientsMap.get(ingredientId);
        if (ingredient != null) {
            ingredientsMap.remove(ingredientId);
        } else throw new IllegalArgumentException("Указанного ингредиенты нет в списке");
        return ingredientsMap;
    }
}


