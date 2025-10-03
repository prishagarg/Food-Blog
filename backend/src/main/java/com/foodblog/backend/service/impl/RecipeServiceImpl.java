package com.foodblog.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.User;
import com.foodblog.backend.repository.RecipeRepository;
import com.foodblog.backend.repository.UserRepository;
import com.foodblog.backend.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Recipe createRecipe(Recipe recipe) {
        // Save new recipe
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        // Find existing recipe or throw
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Update fields
        existing.setTitle(updatedRecipe.getTitle());
        existing.setDescription(updatedRecipe.getDescription());
        existing.setCuisine(updatedRecipe.getCuisine());
        // Add more field updates as needed

        return recipeRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Recipe getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        return recipeRepository.save(recipe);
    }

    @Override
    public Page<Recipe> listRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Page<Recipe> searchRecipesByTitle(String title, Pageable pageable) {
        return recipeRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Page<Recipe> searchRecipesByCuisine(String cuisine, Pageable pageable) {
        return recipeRepository.findByCuisineIgnoreCase(cuisine, pageable);
    }

    @Override
    @Transactional
    public void likeRecipe(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getLikedRecipes().add(recipe);
        recipe.getLikedBy().add(user);

        userRepository.save(user);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void unlikeRecipe(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getLikedRecipes().remove(recipe);
        recipe.getLikedBy().remove(user);

        userRepository.save(user);
        recipeRepository.save(recipe);
    }
}
