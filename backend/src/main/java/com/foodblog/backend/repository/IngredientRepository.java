package com.foodblog.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodblog.backend.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameContainingIgnoreCase(String name);
    // TODO: Add method to find recipes by ingredient name when search is implemented
    // List<Recipe> findRecipesByIngredientName(String ingredientName);
}