package me.ruana.recipeBook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ruana.recipeBook.dto.IngredientsDTO;
import me.ruana.recipeBook.model.Ingredients;
import me.ruana.recipeBook.services.IngredientsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Список ингредиентов", description = "Добавление, редактирование, просмотр ингредиентов, удаление ингредиентов из списка.")
public class IngredientsController {
    private final IngredientsServiceImpl ingredientsService;

    public IngredientsController(IngredientsServiceImpl ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    // ПОЛУЧЕНИЕ ИНГРЕДИЕНТА ПО ID:
    @GetMapping("/{id}")
    @Operation(summary = "Поиск и просмотр ингредиента из списка",
            description = "Поиск ингредиента по ID")
    public ResponseEntity<IngredientsDTO> getIngredient(@PathVariable("id") int id) {
        IngredientsDTO ingredientsDTO = ingredientsService.getIngredient(id);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // ДОБАВЛЕНИЕ ИНГРЕДИЕНТА В СПИСОК:
    @PostMapping
    @Operation(summary = "Добавление ингредиента в список",
            description = "Создание и добавление ингредиента в список")
    public ResponseEntity<IngredientsDTO> addIngredient(@RequestBody Ingredients ingredient) {
        IngredientsDTO ingredientsDTO = ingredientsService.addIngredientToMap(ingredient);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // ПРАВКА ИНГРЕДИЕНТА ПО ID:
    @PutMapping("/{id}")
    @Operation(summary = "Корректировка ингредиента",
            description = "Поиск ингредиента в списке по ID и редактирование найденного ингредиента")
    public ResponseEntity<IngredientsDTO> changeIngredient(@PathVariable("id") int id, @RequestBody Ingredients ingredient) {
        IngredientsDTO ingredientsDTO = ingredientsService.editIngredient(id, ingredient);
        return ResponseEntity.ok().body(ingredientsDTO);
    }

    // УДАЛЕНИЕ ИНГРЕДИЕНТА ПО ID:
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента",
            description = "Поиск ингредиента в списке по ID и удаление найденного ингредиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингредиент найден в списке",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientsDTO.class)))})
    })
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") int id) {
        var allIngredientsMap = ingredientsService.deleteIngredient(id);
        return ResponseEntity.ok().body(allIngredientsMap);
    }

    // СПИСОК ИНГРЕДИЕНТОВ:
    @GetMapping
    @Operation(summary = "Просмотр списка всех ингредиентов",
            description = "Выводит все ингредиенты, добавленные в список")
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse(responseCode = "200",
                    description = "Список ингредиентов.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientsDTO.class)))})
    })
    public ResponseEntity<Map<Integer, Ingredients>> getIngredientsList() {
        var allIngredientsMap = ingredientsService.getIngredientsMap();
        return ResponseEntity.ok().body(allIngredientsMap);
    }
}
