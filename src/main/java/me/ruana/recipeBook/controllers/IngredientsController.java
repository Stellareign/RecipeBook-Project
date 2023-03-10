package me.ruana.recipeBook.controllers;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.services.IngredientsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    // ПОЛУЧЕНИЕ ИНГРЕДИЕНТА ПО ID:
    @GetMapping("/{id}")
    public IngredientsDTO getIndredient(@PathVariable("id") int id) {
        return ingredientsService.getIngredient(id);
    }

    // ДОБАВЛЕНИЕ ИНГРЕДИЕНТА В СПИСОК:
    @PostMapping
    public IngredientsDTO addIngredient(@RequestBody Ingredients ingredient) {
        return ingredientsService.addIngredientToMap(ingredient);
    }

    // ПРАВКА ИНГРЕДИЕНТА ПО ID:
    @PutMapping("/edit/{id}")
    public IngredientsDTO changeIngredient(@PathVariable("id") int id, @RequestBody Ingredients ingredient) {
        return ingredientsService.editIngredient(id, ingredient);
    }

    // УДАЛЕНИЕ ИНГРЕДИЕНТА ПО ID:
    @DeleteMapping("/delete/{id}")
    public Map<Integer, Ingredients> deleteIngredient(@PathVariable("id") int id) {
        return ingredientsService.deleteIngredient(id);
    }

    // СПИСОК ИНГРЕДИЕНТОВ:
    @GetMapping
    public Map<Integer, Ingredients> getIngredientsList() {
        return ingredientsService.getIngredientsMap();
    }
}
