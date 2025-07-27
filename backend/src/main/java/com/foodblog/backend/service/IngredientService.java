package com.foodblog.backend.service;

import java.util.List;

import com.foodblog.backend.model.Ingredient;
import com.foodblog.backend.model.Recipe;


public interface IngredientService{
    Ingredient createIngredient(Ingredient ingredient);
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long id);
    Ingredient updateIngredient(Long id, Ingredient ingredient);
    void deleteIngredient(Long id);
    List<Ingredient> searchIngredientsByName(String name);
    List<Recipe> getRecipesByIngredientName(String name);
}