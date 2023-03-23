package me.ruana.recipeBook.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileService {


    // ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveRecipeToFile(String json);

    // ОЧИСТКА ФАЙЛА:
    boolean cleanRecipesDataFile();

    // первый метод
    File getDataFileRecipes();

    // ПРОВЕРКА НАЛИЧИЯ ФАЙЛА С РЕЦЕПТАМИ ПЕРЕД ЗАГРУЗКОЙ:
    File checkExistsRecipesFile() throws FileNotFoundException;

    // ЗАМЕНА ФАЙЛА С РЕЦЕПТАМИ НА СЕРВЕРЕ:
    boolean uploadRecipeDataFile(MultipartFile uploadedRecipesFile);


    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readRecipeFromFile();


    // МЕТОД ДЛЯ РЕЦЕПТОВ НА СЕРВЕР БЕЗ БИБЛИОТЕКИ: КАК РЕШИТЬ ВОПРОС С КОДИРОКОЙ UTF-8?
    boolean uploadRecipeDataFile2(MultipartFile uploadedRecipesFile2);

    // ======================================МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ: =================================
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    boolean saveIngredientsListToFile(String json);


    //ЧТЕНИЕ ИЗ ФАЙЛА:
    String readIngredientFromFile();

    // ОЧИСТКА ФАЙЛА:
    boolean cleanIngredientsDataFile();

    // ПОЛУЧЕНИЕ ФАЙЛА С ИНГРЕДИЕНТАМИ:
    File getDataFileIngredients();

    // ЗАМЕНА ФАЙЛА НА СЕРВЕРЕ (пренос из контроллера):
    boolean uploadIngredientDataFile(MultipartFile uploadedIngredientFile);

    // ПРОВЕРКА НАЛИЧИЯ ФАЙЛА С ИНГРЕДИЕНТАМИ ПЕРЕД ЗАГРУЗКОЙ НА СЕРВЕР:
    File checkExistsIngredientsFile() throws FileNotFoundException;
}
