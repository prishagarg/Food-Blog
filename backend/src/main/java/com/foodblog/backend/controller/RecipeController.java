package com.foodblog.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping
    public Page<Recipe> listRecipes(Pageable pageable) {
        return recipeService.listRecipes(pageable);
    }

    @GetMapping("/search/title")
    public Page<Recipe> searchRecipesByTitle(@RequestParam String title, Pageable pageable) {
        return recipeService.searchRecipesByTitle(title, pageable);
    }

    @GetMapping("/search/cuisine")
    public Page<Recipe> searchRecipesByCuisine(@RequestParam String cuisine, Pageable pageable) {
        return recipeService.searchRecipesByCuisine(cuisine, pageable);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likeRecipe(@PathVariable Long id, @RequestParam Long userId) {
        recipeService.likeRecipe(id, userId);
    }

    @PostMapping("/{id}/unlike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlikeRecipe(@PathVariable Long id, @RequestParam Long userId) {
        recipeService.unlikeRecipe(id, userId);
    }
}
