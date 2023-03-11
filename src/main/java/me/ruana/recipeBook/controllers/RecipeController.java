package me.ruana.recipeBook.controllers;


import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import me.ruana.recipeBook.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // ПОЛУЧЕНИЕ РЕЦЕПТА ПО ID:
//    @GetMapping("/{id}")
//    public RecipeDTO getRecipe(@PathVariable("id") int id) {
//        return recipeService.getRecipe(id);
//    }
    @GetMapping("/{id}") // переделываем в response:
    public  ResponseEntity <?> getRecipe(@PathVariable("id") int id) {
     RecipeDTO recipeDTO = recipeService.getRecipe(id);
        return  ResponseEntity.ok().body(recipeDTO);
    }

    // ДОБАВЛЕНИЕ НОВОГО РЕЦЕПТА:
//    @PostMapping
//    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {
//        return recipeService.addRecipeToMap(recipe);
//    }
    @PostMapping // переделываем в response:
    public ResponseEntity <?>addRecipe(@RequestBody Recipe recipe) {
        RecipeDTO recipeDTO = recipeService.addRecipeToMap(recipe);
        return ResponseEntity.ok(recipeDTO);
    }

    // ПРАВКА РЕЦЕПТА ПО ID:
//    @PutMapping("/{id}")
//    public RecipeDTO changeRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
//        return recipeService.editRecipe(id, recipe);
//    }
    // ПРАВКА РЕЦЕПТА ПО ID: переделка на response
    @PutMapping("/{id}")
    public ResponseEntity <?> changeRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
       var recipeDTO = recipeService.editRecipe(id, recipe);
        return ResponseEntity.ok().body(recipeDTO);
    }

    // УДАЛЕНИЕ РЕЦЕПТА ПО ID:
//    @DeleteMapping("/{id}")
//    public Map<Integer, Recipe> deleteRecipe(@PathVariable("id") int id) {
//        return recipeService.deleteRecipe(id);
//    }
    @DeleteMapping("/{id}") //переделка на response
    public ResponseEntity <?> deleteRecipe(@PathVariable("id") int id) {
        var recipesMap = recipeService.deleteRecipe(id);
        return ResponseEntity.ok().body(recipesMap);
    }

    // ВСЕ РЕЦЕПТЫ:
//    @GetMapping
//    public Map<Integer, Recipe> getRecipeList() {
//        return recipeService.getRecipeMap();
//    }
    @GetMapping//переделка на response
    public ResponseEntity <?>  getRecipeList() {
        var allRecipesMap = recipeService.getRecipeMap();
        return ResponseEntity.ok().body(allRecipesMap);
    }
}



