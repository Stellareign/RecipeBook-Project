package me.ruana.recipeBook.services;

import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {
    // ДОБАВЛЕНИЕ РЕЦЕПТА:
    RecipeDTO addRecipeToMap(Recipe recipe);

    // ПОЛУЧЕНИЕ РЕЦЕПТА:
    RecipeDTO getRecipe(int recipeId);

    // РЕДАКТИРОВАНИЕ РЕЦЕПТА:
    RecipeDTO editRecipe(int recipeId, Recipe newRecipe);

    // УДАЛЕНИЕ РЕЦЕПТА:
    Map<Integer, Recipe> deleteRecipe(int recipeId);

    // ПОЛУЧЕНИЕ РЕЦЕТА ПО ID (от наставника - ОСОЗНАТЬ):
    List<RecipeDTO> getRecipeByIngredientId(int ingredientId);

    // ПОИСК РЕЦЕПТА ПО НЕСКОЛЬКИМ ИНГРЕДИЕНТАМ:
    List<RecipeDTO> getRecipesByIngredientsIds(List<Integer> ingredientsIds);

    // ПОЛУЧЕНИЕ СПИСКА РЕЦЕПТОВ:
    Map<Integer, Recipe> getRecipeMap();

    // СОХРАНЕНИЕ РЕЦЕПТА В ФАЙЛ:
    void saveRecipeToFile();

    // СЧИТЫВАНИЕ РЕЦЕПТА ИЗ ФАЙЛА:
    void readRecipeFromFile();

    // СОХРАНИТЬ РЕЦЕПТЫ В ТЕКСТОВЫЙ ФАЙЛ:
    Path saveRecipesToTxt() throws IOException, IOException;
}
