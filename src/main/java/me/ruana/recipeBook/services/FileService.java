package me.ruana.recipeBook.services;

import io.github.classgraph.Resource;

import java.io.File;

public interface FileService {


    // ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveRecipeToFile(String json);

    // ОЧИСТКА ФАЙЛА:

    boolean cleanRecipesDataFile();

    // первый метод
    File getDataFileRecipes();

    // МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ.
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveIngredientToFile(String json);

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readRecipeFromFile();

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readIngredientFromFile();

    // ОЧИСТКА ФАЙЛА:
    boolean cleanIngredientsDataFile();

    File getDataFileIngredients(); //

    // второй метод

}
