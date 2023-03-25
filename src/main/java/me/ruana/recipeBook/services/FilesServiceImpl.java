package me.ruana.recipeBook.services;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class FilesServiceImpl implements FileService {
    private RecipeService recipeService;

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

    // ПОЛУЧЕНИЕ ФАЙЛА С РЕЦЕПТАМИ:
    @Override // первый метод
    public File getDataFileRecipes() {
        return new File(dataFilePath, dataFileNameRecipes); //возвращает файл с указанным именем по указанному адресу.
        //Если файла нет, создаёт указанный файл.
    }


    // ПРОВЕРКА НАЛИЧИЯ ФАЙЛА С РЕЦЕПТАМИ ПЕРЕД ЗАГРУЗКОЙ НА СЕРВЕР:
    @Override
    public File checkExistsRecipesFile() throws FileNotFoundException {
        File recipesFile = getDataFileRecipes();
        if (recipesFile.exists()) { // проверяем, существует ли файл

        }
        return recipesFile;
    }

    // ЗАМЕНА ФАЙЛА С РЕЦЕПТАМИ НА СЕРВЕРЕ (загрузка на сервер):
    @Override // перенос из контроллера: всю логику писать в сервисе.
    public boolean uploadRecipeDataFile(MultipartFile uploadedRecipesFile) {
        cleanRecipesDataFile(); // очищаем файл на сервере для перезаписи
        File recipesFile = getDataFileRecipes(); // получаем объект File из методом getDataFileRecipes() из fileService
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) { // Открываем поток для записи в файл с помощью FileOutputStream
            IOUtils.copy(uploadedRecipesFile.getInputStream(), fos); // Копируем содержимое загруженного файла в
            // файл на сервере с помощью метода copy() из библиотеки Apache Commons IO.
            uploadedRecipesFile.getResource();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // МЕТОД ДЛЯ РЕЦЕПТОВ НА СЕРВЕР БЕЗ БИБЛИОТЕКИ: КАК РЕШИТЬ ВОПРОС С КОДИРОКОЙ UTF-8?
    @Override
    public boolean uploadRecipeDataFile2(MultipartFile uploadedRecipesFile2) {
        cleanRecipesDataFile(); // очищаем файл на сервере для перезаписи
        File recipesFile = getDataFileRecipes();
        try (InputStream in = uploadedRecipesFile2.getInputStream(); // из объекта uploadedRecipesFile2 возвращаем поток
             // ввода для чтения данных из файла.
             OutputStream out = new FileOutputStream(recipesFile); // из объекта FileOutputStream возвращаем поток вывода для записи данных в файл
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) { // объект для записи данных в
            // файл с использованием кодировки UTF-8.
            byte[] buffer = new byte[4096]; // задаём размер бефера
            int bytesRead = -1; // значение переменной, обозначающей конец файла (??) - означает, что дальше операция выполняться не может
            while ((bytesRead = in.read(buffer)) != -1) { // пока буфер не равен -1 (??) (можно ли указать > -1?)
                writer.write(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8)); // из объекта writer записываем данные в файл

            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //======================================================== МЕТОДЫ ДЛЯ ИНГРЕДИЕНТОВ: ===================================
// ОЧИСТКА И ПЕРЕЗАПИСЬ ФАЙЛА:
    @Override
    public boolean saveIngredientsListToFile(String json) {
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

    // ЗАМЕНА ФАЙЛА НА СЕРВЕРЕ (пренос из контроллера):
    @Override
    public boolean uploadIngredientDataFile(MultipartFile uploadedIngredientFile) {
        cleanIngredientsDataFile(); // очищаем файл на сервере для перезаписи
        File ingredientsFile = getDataFileIngredients();
        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(uploadedIngredientFile.getInputStream(), fos); // через библиотеку Apache Commons IO » 2.11.0 (добавили в зависимости)
            recipeService.readRecipeFromFile(); // записываем файл в нашу мапу
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File checkExistsIngredientsFile() throws FileNotFoundException {
        File ingredientsFile = getDataFileIngredients();
        if (ingredientsFile.exists()) {// если существует, вернуть
        }
        return ingredientsFile;
    }

    //    @Override // второй метод - не работает :(
//    public Resource getResource(String fileName) {
//        Path dataFilesPath = Path.of(dataFilePath); // создаём объект Path из нашей ссылки на директорию для обработки методом resilve()
//        Path dataPath = dataFilesPath.resolve(fileName + ".json"); // добавляем имя файла и тип к пути к папке
//        return new FileSystemResource(dataPath); // возвращаем объект типа Resource, который представляет файл
//    }


    // СОЗДАНИЕ ВРЕМЕННОГО ФАЙЛА:
    @Override
    public Path createTempRecipeFile(String temporaryFile) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", temporaryFile); // создание временно файла
            // с помощью метода createTempFile класса Files с входным параметром адреса дирректории dataFilePath
            // (префикс файла `tempFile` и суффикс `temporaryFile)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}