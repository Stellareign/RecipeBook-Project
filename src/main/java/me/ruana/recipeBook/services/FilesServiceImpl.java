package me.ruana.recipeBook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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

    // ======================================================= МЕТОДЫ ДЛЯ РЕЦЕПТОВ: ====================================
    // ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileNameRecipes), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // добавить везде, где нет сообщения об ошибке для возможности отладки приложения
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

    @Override
    // ОЧИСТКА ФАЙЛА:
    public boolean cleanRecipesDataFile() {
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

    @Override // первый метод
    public File getDataFileRecipes() {
        return new File(dataFilePath, dataFileNameRecipes); //возвращает файл с указанным именем по указанному адресу.
        //Если файла нет, создаёт указанный файл.
    }

    //======================================================== МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ: ===================================
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    @Override
    public boolean saveIngredientToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, dataFileNameIngredients), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // добавить везде, где нет сообщения об ошибке для возможности отладки приложения
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
    @Override
    public boolean cleanIngredientsDataFile() {
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

    @Override // первый метод
    public File getDataFileIngredients() {
        return new File(dataFilePath, dataFileNameIngredients); //возвращает файл с указанным именем по указанному адресу.
        //Если файла нет, создаёт указанный файл.
    }

//    @Override // второй метод - не работает :(
//    public Resource getResource(String fileName) {
//        Path dataFilesPath = Path.of(dataFilePath); // создаём объект Path из нашей ссылки на директорию для обработки методом resilve()
//        Path dataPath = dataFilesPath.resolve(fileName + ".json"); // добавляем имя файла и тип к пути к папке
//        return new FileSystemResource(dataPath); // возвращаем объект типа Resource, который представляет файл
//    }
}
