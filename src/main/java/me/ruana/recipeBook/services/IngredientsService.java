package me.ruana.recipeBook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsService {
    private static int ingredientId = 0;
    private static Map<Integer, Ingredients> ingredientsMap = new HashMap<>();
    final private FileService fileService; // "заинджектили" класс - добавить в конструктор

    public IngredientsService(FileService fileService) {
        this.fileService = fileService;
    }


    //     МЕТОД, ДЛЯ ВЫЗОВА ФАЙЛА, ХРАНЯЩЕГОСЯ НА ДИСКЕ:
    @PostConstruct
    private void foo() {
        readIngredientFromFile();
    }

    // ДОБАВЛЯЕМ ИНГРДИЕНТ В МАПУ:
    public IngredientsDTO addIngredientToMap(Ingredients ingredient) {
        int id = ingredientId++;
        ingredientsMap.put(id, ingredient);
        saveIngredientToFile();
        return IngredientsDTO.from(id, ingredient); // возвращаем DTO из стат.фабрики
    }

    // ИЗВЛЕКАЕМ ИНГРЕДИЕНТ (по аналогии с рецептами):
    public IngredientsDTO getIngredient(int ingredientId) {
        Ingredients ingredients = ingredientsMap.get(ingredientId);
        if (ingredients != null) {
            return IngredientsDTO.from(ingredientId, ingredients);
        } else throw new IllegalArgumentException("Ингредиент отсутствует в списке");
    }

    // ВСЕ ИНГРЕДИЕНТЫ:
    public Map<Integer, Ingredients> getIngredientsMap() {
        if (ingredientsMap.isEmpty()) {
            throw new ResolutionException("Список ингредиентов пуст!");
        }
        return ingredientsMap;
    }

    // РЕДАКТИРОВАНИЕ ИНГРЕДИЕНТА:
    public IngredientsDTO editIngredient(int ingredientId, Ingredients newIngredient) {
        Ingredients ingredient = ingredientsMap.get(ingredientId);
        if (ingredient != null) {
            ingredient.setIngredientName(newIngredient.getIngredientName());
            ingredient.setNumberOfIngredients(newIngredient.getNumberOfIngredients());
            ingredient.setMeasureUnits(newIngredient.getMeasureUnits());
            saveIngredientToFile();
            return IngredientsDTO.from(ingredientId, ingredient); // возвращаем DTO, состоящий из id и тела ингредиента
        } else throw new IllegalArgumentException("Ингредиента с таким ID нет!");
    }

    // УДАЛЕНИЕ ИНГРЕДИЕНТА:
    public Map<Integer, Ingredients> deleteIngredient(int ingredientId) {
        Ingredients ingredient = ingredientsMap.get(ingredientId);
        if (ingredient != null) {
            ingredientsMap.remove(ingredientId);
        } else throw new IllegalArgumentException("Указанного ингредиенты нет в списке");
        return ingredientsMap;
    }

    // СОХРАНЕНИЕ ИНГРЕДИЕНТА В ФАЙЛ:
    private void saveIngredientToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            fileService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // СЧИТЫВАИНЕ ИНГРЕДИЕНТА ИЗ ФАЙЛА:
    private void readIngredientFromFile() {
        String json = fileService.readIngredientFromFile();
        try {
            ingredientsMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}



