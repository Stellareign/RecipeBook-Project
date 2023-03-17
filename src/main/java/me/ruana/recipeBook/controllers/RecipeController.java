package me.ruana.recipeBook.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Recipe;
import me.ruana.recipeBook.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Книга рецептов", description = "Операции с рецептами: просмотр, редактирование, добавление, удаление")

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
    @Operation(summary = "Поиск и просмотр рецепта",
            description = "Поиск рецепта по ID" )
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
    @Operation(summary = "Добавление рецепта",
            description = "Создание и добавление рецепта в книгу рецептов" )
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
    @Operation(summary = "Изменение рецепта",
            description = "Поиск рецепта по ID и редактирование найденного рецепта" )
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
    @Operation(summary = "Удаление рецепта",
            description = "Поиск рецепта по ID и удаление найденного рецепта" )
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse( responseCode = "200",
            description = "Рецепт найден в книге и удалён",
            content = {@Content( mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = RecipeDTO.class)))})
    })
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
    @Operation(summary = "Просмотр всех рецептов",
            description = "Выводит список всех рецептов, занесённых в книгу" )
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse( responseCode = "200",
                    description = "Все рецепты в книге.",
                    content = {@Content( mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeDTO.class)))})
    })
    public ResponseEntity <?>  getRecipeList() {
        var allRecipesMap = recipeService.getRecipeMap();
        return ResponseEntity.ok().body(allRecipesMap);
    }

    //ПОИСК ПО ИНГРЕДИЕНТУ: не рабоатет почему-то.... проверить
    @GetMapping("/byIngredient/{id}")
    public ResponseEntity<List<RecipeDTO>> getRecipeByIngredientId(@PathVariable("id")int id){
        var recipeListByIngredientId = recipeService.getRecipeByIngredientId(id);
        return ResponseEntity.ok().body(recipeListByIngredientId);
    }

    @GetMapping("/byIngredients")
    public ResponseEntity<List<RecipeDTO>> getRecipesByIngredientsIds(@RequestParam("ids") List<Integer> ids) {
        var recipesListByIngredientsIds = recipeService.getRecipesByIngredientsIds(ids);
        return ResponseEntity.ok().body(recipesListByIngredientsIds);
    }
}



