package me.ruana.recipeBook.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

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

    //    @Override // второй метод - не работает :(
//    public Resource getResource(String fileName) {
//        Path dataFilesPath = Path.of(dataFilePath); // создаём объект Path из нашей ссылки на директорию для обработки методом resilve()
//        Path dataPath = dataFilesPath.resolve(fileName + ".json"); // добавляем имя файла и тип к пути к папке
//        return new FileSystemResource(dataPath); // возвращаем объект типа Resource, который представляет файл
//    }
    Path createTempRecipeFile(String temporaryFile);
}
