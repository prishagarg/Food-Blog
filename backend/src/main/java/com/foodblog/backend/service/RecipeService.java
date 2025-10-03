package com.foodblog.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foodblog.backend.model.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);
    Recipe updateRecipe(Long id, Recipe updatedRecipe);
    void deleteRecipe(Long id);
    Recipe getRecipeById(Long id);
    Page<Recipe> listRecipes(Pageable pageable);
    Page<Recipe> searchRecipesByTitle(String title, Pageable pageable);
    Page<Recipe> searchRecipesByCuisine(String cuisine, Pageable pageable);
    void likeRecipe(Long recipeId, Long userId);
    void unlikeRecipe(Long recipeId, Long userId);
}
