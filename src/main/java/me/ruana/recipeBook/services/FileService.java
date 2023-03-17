package me.ruana.recipeBook.services;

public interface FileService {


    // ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveRecipeToFile(String json);

    // МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ.
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveIngredientToFile(String json);

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readRecipeFromFile();

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readIngredientFromFile();
}
