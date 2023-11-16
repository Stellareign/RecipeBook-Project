package me.ruana.recipeBook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.module.ResolutionException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int recipeId = 0;
    private static Map<Integer, Recipe> recipeMap = new HashMap<>();
    private final FileService fileService; // "заинджектили" класс - добавить в конструктор

    private final IngredientsServiceImpl ingredientsService; // создаём для метода получения рецепта по ингредиенту - обязательно создать конструктор!

    public RecipeServiceImpl(FileService fileService, IngredientsServiceImpl ingredientsService) {
        this.fileService = fileService; // "заинджектили" класс
        this.ingredientsService = ingredientsService;
    }

    // МЕТОД, ДЛЯ ВЫЗОВА ФАЙЛА, ХРАНЯЩЕГОСЯ НА ДИСКЕ:
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.data.file1}")
    private String dataFileNameRecipes;

    @PostConstruct
    private void foo() {
        File file = new File(dataFilePath, dataFileNameRecipes);
        if (file.exists())
            readRecipeFromFile();
    }

    // ДОБАВЛЕНИЕ РЕЦЕПТА:
    @Override
    public RecipeDTO addRecipeToMap(Recipe recipe) { // добавление рецепта в мапу
        int id = recipeId++;
        recipeMap.put(id, recipe);
        saveRecipeToFile(); // добавили метод сохранения в файл
        return RecipeDTO.from(id, recipe); // возвращаем DTO из стат.фабрики
    }

    // ПОЛУЧЕНИЕ РЕЦЕПТА:
    @Override
    public RecipeDTO getRecipe(int recipeId) { // извлекаем нужный рецепт
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null && recipeMap.containsKey(recipeId)) {
            return RecipeDTO.from(recipeId, recipe);
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
    }

    // РЕДАКТИРОВАНИЕ РЕЦЕПТА:
    @Override
    public RecipeDTO editRecipe(int recipeId, Recipe newRecipe) { // извлекаем нужный рецепт
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null) {
            recipe.setTitle(newRecipe.getTitle());
            recipe.setCookingTime(newRecipe.getCookingTime());
            recipe.setMeasureTime(newRecipe.getMeasureTime());
            recipe.setIngredientsList(newRecipe.getIngredientsList());
            recipe.setCookingStepsList(newRecipe.getCookingStepsList());
            saveRecipeToFile();                                               // добавили метод сохранения в файл для обновления файла
            return RecipeDTO.from(recipeId, recipe);
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
    }

    // УДАЛЕНИЕ РЕЦЕПТА:
    @Override
    public Map<Integer, Recipe> deleteRecipe(int recipeId) {
        Recipe recipe = recipeMap.get(recipeId);
        if (recipe != null) {
            recipeMap.remove(recipeId);
            saveRecipeToFile();
        } else throw new IllegalArgumentException("Рецепта с таким ID нет!");
        return recipeMap;
    }

    // ПОЛУЧЕНИЕ РЕЦЕТА ПО ID (от наставника - ОСОЗНАТЬ):
    @Override
    public List<RecipeDTO> getRecipeByIngredientId(int ingredientId) { // метод будет возвращать список (лист) рецептов с указанным ингредиентом
        Ingredients ingredient = this.ingredientsService.getIngredientsMap().get(ingredientId); // извлекаем ингредиент по указанному ID
        if (ingredient != null) { // если ингредиент по ID найден, то возвращаем:
            return this.recipeMap.entrySet() // проходимся по мапе рецептов
                    .stream()
                    .filter(e -> e.getValue().getIngredientsList().stream() // фильтруем рецепты, извлекая из листа ингредиентов  ...
                            .anyMatch(i -> i.getIngredientName().equals(ingredient.getIngredientName()))) // ... имя ингредиента, и если оно совпадает с указанным
                    .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))// извлекаем рецепт, формируя DTO
                    .collect(Collectors.toList()); // и добавляем его в лист
        } else throw new IllegalArgumentException("Ингредиент не найден");
    }

    // ПОИСК РЕЦЕПТА ПО НЕСКОЛЬКИМ ИНГРЕДИЕНТАМ:
    @Override
    public List<RecipeDTO> getRecipesByIngredientsIds(List<Integer> ingredientsIds) { // принимает на вход список ID ингредиентов
        ingredientsIds = new ArrayList<>(ingredientsService.getIngredientsMap().keySet()); // получаем лист ID ингредиентов по ключам мапы ингредиентов
        List<String> ingredientsNames = ingredientsIds.stream() // создаём список рецептов, сопоставленных с ID ингредиента
                .map(this.ingredientsService::getIngredient)
                .filter(Objects::nonNull) // отфильтровываем пустые значения
                .map(IngredientsDTO::getIngredientName)
                .toList();

        return this.recipeMap.entrySet()
                .stream()
                .filter(e -> { // фильтруем мапу рецептов по именам заданных ингредиентов
                    Set<String> recipeIngredientsNames = e.getValue()
                            .getIngredientsList()
                            .stream()
                            .map(Ingredients::getIngredientName)
                            .collect(Collectors.toSet());
                    return recipeIngredientsNames.containsAll(ingredientsNames);
                })
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))// собираем в список рецепты, содержащие нужные ингредиенты
                .collect(Collectors.toList());
    }

    // ПОЛУЧЕНИЕ СПИСКА РЕЦЕПТОВ:
    @Override
    public Map<Integer, Recipe> getRecipeMap() {
        if (recipeMap.isEmpty()) {
            throw new ResolutionException("Книга рецептов пуста!");
        } else return recipeMap;
    }

    // СОХРАНЕНИЕ РЕЦЕПТА В ФАЙЛ:
    @Override
    public void saveRecipeToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // СЧИТЫВАНИЕ РЕЦЕПТА ИЗ ФАЙЛА:
    @Override
    public void readRecipeFromFile() {
        try {
            String json = fileService.readRecipeFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
// СОХРАНИТЬ РЕЦЕПТЫ В ТЕКСТОВЫЙ ФАЙЛ:
    @Override
    public Path saveRecipesToTxt() throws IOException {
        Path path = fileService.createTempRecipeFile("temprRecipes"); // задаём путь к временному файлу
        for (Recipe recipe : recipeMap.values()) { // итерируемся по рецептам в мапе
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
              //  writer.write(recipeId);
                writer.append((++recipeId) + " " + recipe.toString()); // запись из тустринга класса Recipe, созданного
                // конструктором StringBuilder с добавлением номера к названию, начиная с 1
                writer.append("\n"); // каждый объект с новой строки
            }
        }
        return path;
    }
}