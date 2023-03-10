package me.ruana.recipeBook.controllers;


import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import me.ruana.recipeBook.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // ПОЛУЧЕНИЕ РЕПТА ПО ID:
    @GetMapping("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    // ДОБАВЛЕНИЕ НОВОГО РЕЦЕПТА:
    @PostMapping
    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipeToMap(recipe);
    }

    // ПРАВКА РЕЦЕПТА ПО ID:
    @PutMapping("/edit/{id}")
    public RecipeDTO changeRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
        return recipeService.editRecipe(id, recipe);
    }

    // УДАЛЕНИЕ РЕЦЕПТА ПО ID:
    @DeleteMapping("/delete/{id}")
    public Map<Integer, Recipe> deleteRecipe(@PathVariable("id") int id) {
        return recipeService.deleteRecipe(id);
    }

    // ВСЕ РЕЦЕПТЫ:
    @GetMapping
    public Map<Integer, Recipe> getRecipeList() {
        return recipeService.getRecipeMap();
    }

}



