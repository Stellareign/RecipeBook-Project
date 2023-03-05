package me.ruana.recipeBook.controllers;


import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import org.springframework.web.bind.annotation.*;
import me.ruana.recipeBook.services.RecipeService;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
   private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}") // получение рецепта по параметру id
         public RecipeDTO getRecipe(@PathVariable("id") int id){
        return recipeService.getRecipe(id);
    }

    @PostMapping // добавление рецепта
    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipeToMap(recipe);
    }
    }


