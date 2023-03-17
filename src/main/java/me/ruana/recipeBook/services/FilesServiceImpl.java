package me.ruana.recipeBook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FileService {

    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.data.file1}")
    private String dataFileNameRecipes;
    @Value("${name.of.data.file2}")
    private String dataFileNameIngredients;

    // МЕТОДЫ ДЛЯ РЕЦЕПТОВ.
    // ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileNameRecipes), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // добавить везде, где нет сообщения об ошибки для возможности отладки приложения
            return false;
        }
    }

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameRecipes)); //считывание файла, расположенного по адресу
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // ОЧИСТКА ФАЙЛА:
    private boolean cleanRecipesDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileNameRecipes);
            Files.deleteIfExists(path); // удалить, если существует
            Files.createFile(path); // создание пустого файла
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ.
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    @Override
    public boolean saveIngredientToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileNameIngredients), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // добавить везде, где нет сообщения об ошибки для возможности отладки приложения
            return false;
        }
    }

    //ЧТЕНИЕ ИЗ ФАЙЛА:
    @Override
    public String readIngredientFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameIngredients)); //считывание файла, расположенного по адресу
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // ОЧИСТКА ФАЙЛА:
    private boolean cleanIngredientsDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileNameIngredients);
            Files.deleteIfExists(path); // удалить, если существует
            Files.createFile(path); // создание пустого файла
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
