package com.foodblog.backend.service.impl;



import java.util.List;

import org.springframework.stereotype.Service;

import com.foodblog.backend.model.Ingredient;
import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.repository.IngredientRepository;
import com.foodblog.backend.repository.RecipeRepository;
import com.foodblog.backend.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    @Override 
    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Long id){
        return ingredientRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient updated){
        Ingredient existing = getIngredientById(id);
        existing.setName(updated.getName());
        return ingredientRepository.save(existing);
    }

    @Override
    public void deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
    }

    @Override
    public List<Recipe> getRecipesByIngredientName(String name){
        return recipeRepository.findByAnyIngredientMatch(List.of(name));
    }

    @Override
    public List<Ingredient> searchIngredientsByName(String name){
        return ingredientRepository.findByNameContainingIgnoreCase(name);
    }

    
    
}