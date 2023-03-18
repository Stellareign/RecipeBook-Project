package me.ruana.recipeBook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.services.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@Tag(name = "Работа с фалами", description = "Загрузка, выгрузка файлов с рецептами и ингредиентами.")
@RequestMapping("/files")
public class FilesController {
    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }
    // ================================== РЕЦЕПТЫ =========================

    // ЗАГРУЗКА ФАЙЛА С РЕЦЕПТАМИ С СЕРВЕРА:
    @GetMapping(value = "/export/recipes") // для сервера, на кот. делаем запрос, это экспорт
    @Operation(summary = "Загрузка файла с рецептами с сервера",
            description = "Загружает файл с рецептами с сервера на диск пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к загрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeDTO.class)))}) // схема содержания загруженного файла
    })
    public ResponseEntity<InputStreamResource> downloadRecipes() throws FileNotFoundException {
        File recipesFile = fileService.getDataFileRecipes();

        if (recipesFile.exists()) { // проверяем, существует ли файл
            InputStreamResource resource = new InputStreamResource((new FileInputStream(recipesFile)));// формируем входной поток из файла
            return ResponseEntity.ok() //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // заголовок скачивает по ссылке, хотя и по кнопке было норм. - разобраться (vj;yj
                    .contentLength((recipesFile.length())) // заголовок для проверки соответствия размера скачанного файла размеру файла на сервере
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesDataFile.json\"") // заголовок для загрузки файла с нужным именем
                    .body(resource); // возвращаем файл в теле объекта resource
        } else return ResponseEntity.noContent().build();
    }
// после добавления  .contentType(MediaType.APPLICATION_OCTET_STREAM) и .header(HttpHeaders.CONTENT_DISPOSITION файл скачивается в корявом виде

    // ЗАМЕНА ФАЙЛА С РЕЦЕПТАМИ НА СЕРВЕРЕ:
    @PostMapping(value = "import/recipes", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с рецептами на сервер - метод с использованием библиотеки Apache Commons IO",
            description = "Загружает файл с рецептами на сервер с диска пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к выгрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeDTO.class)))}) // схема содержания загруженного файла
    })
    public ResponseEntity<Void> uploadRecipeDataFile(@RequestParam MultipartFile uploadedRecipesFile) {
        fileService.cleanRecipesDataFile(); // очищаем файл на сервере для перезаписи
        File recipesFile = fileService.getDataFileRecipes(); // получаем объект File из методом getDataFileRecipes() из fileService
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) { // Открываем поток для записи в файл с помощью FileOutputStream
            IOUtils.copy(uploadedRecipesFile.getInputStream(), fos); // Копируем содержимое загруженного файла в
            // файл на сервере с помощью метода copy() из библиотеки Apache Commons IO.
            return ResponseEntity.ok()
                    .build(); // если файл скопирован успешно, сообщаем клиенту, что всё ОК
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // или сообщаем об ошибке, если что-то пошло не так
                .build();
    }

// МЕТОД ДЛЯ РЕЦЕПТОВ БЕЗ БИБЛИОТЕК: КАК РЕШИТЬ ВОПРОС С КОДИРОКОЙ UTF-8
@PostMapping(value = "import/recipes2", consumes = MULTIPART_FORM_DATA_VALUE)
@Operation(summary = "Загрузка файла с рецептами на сервер - метод без использования библиотеки",
        description = "Загружает файл с рецептами на сервер с диска пользователя")
    public ResponseEntity<Void> uploadRecipeDataFile2(@RequestParam MultipartFile uploadedRecipesFile2) {
        fileService.cleanRecipesDataFile(); // очищаем файл на сервере для перезаписи
        File recipesFile = fileService.getDataFileRecipes();
        try (BufferedInputStream bis = new BufferedInputStream(uploadedRecipesFile2.getInputStream());
             FileOutputStream fos = new FileOutputStream(recipesFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            while (bis.read(buffer) > 0) {
                bos.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return ResponseEntity.ok().build();
    }
    // ====================== ИНГРЕДИЕНТЫ: ===================================
    // ЗАГРУЗКА ФАЙЛА С ИНГРЕДИЕНТАМИ С СЕРВЕРА:
    @GetMapping(value = "/export/ingredients") // для сервера, на кот. делаем запрос, это экспорт
    @Operation(summary = "Загрузка файла с ингредиентами с сервера.",
            description = "Загружает файл с ингредиентами с сервера на диск пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к загрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientsDTO.class)))}) // схема содержания загруженного файла
    })
    public ResponseEntity<InputStreamResource> downloadIngredientList() throws FileNotFoundException {
        File ingredientsFile = fileService.getDataFileIngredients();

        if (ingredientsFile.exists()) { // проверяем, существует ли файл
            InputStreamResource resource = new InputStreamResource((new FileInputStream(ingredientsFile)));// формируем входной поток из файла
            return ResponseEntity.ok() //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // заголовок скачивает по ссылке, хотя и по кнопке было норм. - разобраться (vj;yj
                    .contentLength((ingredientsFile.length())) // заголовок для проверки соответствия размера скачанного файла размеру файла на сервере
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientsList.json\"") // заголовок для загрузки файла с нужным именем
                    .body(resource); // возвращаем файл в теле объекта resource
        } else return ResponseEntity.noContent().build();
    }

    // ЗАМЕНА ФАЙЛА С ИНГРЕДИЕНТАМИ НА СЕРВЕРЕ:
    @PostMapping(value = "import/ingredients", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с ингредиентами на сервер",
            description = "Загружает файл с ингредиентами на сервер с диска пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к выгрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientsDTO.class)))}) // схема содержания загруженного файла
    })
    public ResponseEntity<Void> uploadIngredientDataFile(@RequestParam MultipartFile uploadedIngredientFile) {
        fileService.cleanIngredientsDataFile(); // очищаем файл на сервере для перезаписи
        File ingredientsFile = fileService.getDataFileIngredients();
        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(uploadedIngredientFile.getInputStream(), fos); // через библиотеку Apache Commons IO » 2.11.0 (добавили в зависимости)
            return ResponseEntity.ok()
                    .build(); // если файл скопирован успешно, сообщаем клиенту, что всё ОК
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // или сообщаем об ошибке, если что-то пошло не так
                .build();
    }
}
