package me.ruana.recipeBook.controllers;

import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.services.IngredientsService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity <IngredientsDTO> getIndredient(@PathVariable("id") int id) {
        IngredientsDTO ingredientsDTO =  ingredientsService.getIngredient(id);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // ДОБАВЛЕНИЕ ИНГРЕДИЕНТА В СПИСОК:
    @PostMapping
    public ResponseEntity <IngredientsDTO> addIngredient(@RequestBody Ingredients ingredient) {
        IngredientsDTO ingredientsDTO = ingredientsService.addIngredientToMap(ingredient);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // ПРАВКА ИНГРЕДИЕНТА ПО ID:
    @PutMapping("/{id}")
    public ResponseEntity <IngredientsDTO> changeIngredient(@PathVariable("id") int id, @RequestBody Ingredients ingredient) {
        IngredientsDTO ingredientsDTO = ingredientsService.editIngredient(id, ingredient);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // УДАЛЕНИЕ ИНГРЕДИЕНТА ПО ID:
    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteIngredient(@PathVariable("id") int id) {
        var allIngredientsMap = ingredientsService.deleteIngredient(id);
        return ResponseEntity.ok().body(allIngredientsMap);
    }

    // СПИСОК ИНГРЕДИЕНТОВ:
    @GetMapping
    public ResponseEntity <Map<Integer, Ingredients>> getIngredientsList() {
        var allIngredientsMap = ingredientsService.getIngredientsMap();
        return ResponseEntity.ok().body(allIngredientsMap);
    }
}
