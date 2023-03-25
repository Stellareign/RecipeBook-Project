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
import me.ruana.recipeBook.services.RecipeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@Tag(name = "Работа с файлами", description = "Загрузка, выгрузка файлов с рецептами и ингредиентами.")
@RequestMapping("/files")
public class FilesController {
    private final FileService fileService;
    private final RecipeService recipeService;

    public FilesController(FileService fileService, RecipeService recipeService) {
        this.fileService = fileService;
        this.recipeService = recipeService;
    }


    // ================================================================= РЕЦЕПТЫ =========================

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
        File recipeFile = fileService.checkExistsRecipesFile();
        InputStreamResource resource = new InputStreamResource((new FileInputStream(recipeFile)));// формируем входной поток из файла
        return ResponseEntity.ok() //
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // заголовок скачивает по ссылке, хотя и по кнопке было норм. - разобраться (vj;yj
                .contentLength(recipeFile.length())// заголовок для проверки соответствия размера скачанного файла размеру файла на сервере
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesDataFile.json\"") // заголовок для загрузки
                // файла с нужным именем
                .body(resource); // возвращаем файл в теле объекта resource
    }

    //ЗАГРУЗКА ФАЙЛА С РЕЦЕПТАМИ  В ФОРМАТЕ .txt:
    @GetMapping(value = "/export/recipes/txt") // для сервера, на кот. делаем запрос, это экспорт
    @Operation(summary = "Загрузка файла с рецептами с сервера в формате (.txt)",
            description = "Загружает файл с рецептами с сервера на диск пользователя в текстовом формате")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к загрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeDTO.class)))}), // схема содержания загруженного файла
            @ApiResponse(
                    responseCode = "400", description = "Ошибка в параметрах запроса"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Неверный URL или команда"
            ),
            @ApiResponse(
                    responseCode = "500", description = "Извините, при выполнении запроса произошла ошибка на сервере"
            )
    })
    public ResponseEntity<Object> downloadRecipeFileTxt() {
        try {
            Path path = recipeService.saveRecipesToTxt(); // указываем путь по которому сохраняем текстовый файл
            if (Files.size(path) == -1) { // проверяем, существует ли файл
                return ResponseEntity.noContent().build(); // если файла нет, возвращаем пустой объект
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile())); // создаём входящий поток для чтений файла
            return ResponseEntity.ok() // возвращаем наш файл
                    .contentType(MediaType.TEXT_PLAIN)// устанавливаем тип содержимого
                    .contentLength(Files.size(path)) // проверяем длину
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.txt\"") // задаём имя файла
                    .body(resource);
        } catch (IOException e) { // отлавливаем ошибки
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    // ЗАГРУЗКА ФАЙЛА С РЕЦЕПТАМИ НА СЕРВЕР:
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
        if (fileService.uploadRecipeDataFile(uploadedRecipesFile)) {
            recipeService.readRecipeFromFile();
            return ResponseEntity.ok()
                    .build(); // если файл скопирован успешно, сообщаем клиенту, что всё ОК}
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // или сообщаем об ошибке, если что-то пошло не так
                .build();
    }

    // МЕТОД ДЛЯ РЕЦЕПТОВ БЕЗ БИБЛИОТЕКИ: КАК РЕШИТЬ ВОПРОС С КОДИРОКОЙ UTF-8?
    @PostMapping(value = "import/recipes2", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с рецептами на сервер - метод без использования библиотеки",
            description = "Загружает файл с рецептами на сервер с диска пользователя")
    public ResponseEntity<Void> uploadRecipeDataFile2(@RequestParam MultipartFile uploadedRecipesFile2) { // метод от наставника - РАЗОБРАТЬ!
        if (fileService.uploadRecipeDataFile2(uploadedRecipesFile2)) {
            recipeService.readRecipeFromFile(); // читаем содержимое файла и добавляем в мапу
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // или сообщаем об ошибке, если что-то пошло не так
                .build();
    }

    // ====================================================== ИНГРЕДИЕНТЫ: ===================================
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
//        File ingredientsFile = fileService.getDataFileIngredients();
//        if (ingredientsFile.exists()) { // проверяем, существует ли файл
        File ingredientsFile = fileService.checkExistsIngredientsFile();
        InputStreamResource resource = new InputStreamResource((new FileInputStream(ingredientsFile)));// формируем входной поток из файла
        return ResponseEntity.ok() //
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // заголовок скачивает по ссылке, хотя и по кнопке было норм. - разобраться (vj;yj
                .contentLength((ingredientsFile.length())) // заголовок для проверки соответствия размера скачанного файла размеру файла на сервере
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientsList.json\"") // заголовок для загрузки файла с нужным именем
                .body(resource); // возвращаем файл в теле объекта resource
    }

    // ЗАМЕНА ФАЙЛА С ИНГРЕДИЕНТАМИ НА СЕРВЕРЕ (загрузка файла на сервер):
    @PostMapping(value = "import/ingredients", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с ингредиентами на сервер",
            description = "Загружает файл с ингредиентами на сервер с диска пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Файл найден и готов к выгрузке. Нажмите [Download file]",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientsDTO.class)))}) // схема содержания загруженного файла
    })
    public ResponseEntity<Void> uploadIngredientDataFile(@RequestParam MultipartFile uploadedIngredientFile) { // логика перенесена в сервис
        if (fileService.uploadIngredientDataFile(uploadedIngredientFile)) {
            return ResponseEntity.ok()
                    .build(); // если файл скопирован успешно, сообщаем клиенту, что всё ОК
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // или сообщаем об ошибке, если что-то пошло не так
                .build();

    }
}
