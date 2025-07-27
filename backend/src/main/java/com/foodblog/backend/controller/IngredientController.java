package com.foodblog.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodblog.backend.model.Ingredient;
import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.service.IngredientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController{
    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredient));
    }
    
    @GetMapping
    public ResponseEntity<List<Ingredient>> getALlIngredients(){
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable Long id,
            @RequestBody Ingredient updatedIngredient
    ) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, updatedIngredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ingredient>> searchIngredientsByName(@RequestParam String name) {
        return ResponseEntity.ok(ingredientService.searchIngredientsByName(name));
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getRecipesByIngredientName(@RequestParam String name) {
        return ResponseEntity.ok(ingredientService.getRecipesByIngredientName(name));
    }
}