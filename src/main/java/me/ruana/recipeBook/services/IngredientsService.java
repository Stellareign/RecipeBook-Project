package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientsService {
    private static int ingredientId = 0;
    private final static Map<Integer, Ingredients> ingredientsMap = new HashMap<>();

    // ДОБАВЛЯЕМ ИНГРДИЕНТ В МАПУ:
public IngredientsDTO addIngredientToMap(Ingredients ingredient) {
    int id = ingredientId++;
    ingredientsMap.put(id, ingredient);
   return IngredientsDTO.from(id, ingredient); // возвращаем DTO из стат.фабрики
}

// ИЗВЛЕКАЕМ ИНГРЕДИЕНТ (по аналогии с рецептами):
    public static IngredientsDTO getIngredient(int ingredientId) {
    Ingredients ingredients = ingredientsMap.get(ingredientId);
        if (ingredients != null && ingredientsMap.containsKey(ingredientId)) {
            return IngredientsDTO.from(ingredientId, ingredients);
        } else throw new IllegalArgumentException("Ингредиент отсутствует в рецепте");
    }
}


