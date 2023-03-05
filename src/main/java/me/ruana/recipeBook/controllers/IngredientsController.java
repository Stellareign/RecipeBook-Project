package me.ruana.recipeBook.controllers;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.dto.RecipeDTO;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.services.IngredientsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/{id}")
    public IngredientsDTO getIndredient (@PathVariable("id") int id) {
        return ingredientsService.getIngredient(id);
    }

    @PostMapping
    public IngredientsDTO addIngredient(@RequestBody Ingredients ingredient) {
        return ingredientsService.addIngredientToMap(ingredient);
    }
}
