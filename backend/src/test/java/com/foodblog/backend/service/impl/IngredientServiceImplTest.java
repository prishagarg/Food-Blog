package com.foodblog.backend.service.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.foodblog.backend.model.Ingredient;
import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.repository.IngredientRepository;
import com.foodblog.backend.repository.RecipeRepository;

class IngredientServiceImplTest {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        ingredientRepository = mock(IngredientRepository.class);
        recipeRepository = mock(RecipeRepository.class);
        ingredientService = new IngredientServiceImpl(ingredientRepository, recipeRepository);
    }

    @Test
    void testCreateIngredient() {
        Ingredient ingredient = new Ingredient(null, "Tomato");
        Ingredient saved = new Ingredient(1L, "Tomato");

        when(ingredientRepository.save(ingredient)).thenReturn(saved);

        Ingredient result = ingredientService.createIngredient(ingredient);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getName(), result.getName());
    }

    @Test
    void testGetAllIngredients() {
        List<Ingredient> ingredients = List.of(
                new Ingredient(1L, "Salt"),
                new Ingredient(2L, "Sugar")
        );
        when(ingredientRepository.findAll()).thenReturn(ingredients);

        List<Ingredient> result = ingredientService.getAllIngredients();
        assertEquals(2, result.size());
    }

    @Test
    void testGetIngredientById() {
        Ingredient ingredient = new Ingredient(1L, "Garlic");
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        Ingredient result = ingredientService.getIngredientById(1L);
        assertEquals("Garlic", result.getName());
    }

    @Test
    void testGetIngredientById_NotFound() {
        when(ingredientRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            ingredientService.getIngredientById(2L);
        });
    }

    @Test
    void testUpdateIngredient() {
        Ingredient existing = new Ingredient(1L, "Chili");
        Ingredient updated = new Ingredient(1L, "Green Chili");

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(updated);

        Ingredient result = ingredientService.updateIngredient(1L, updated);
        assertEquals("Green Chili", result.getName());
    }

    @Test
    void testDeleteIngredient() {
        ingredientService.deleteIngredient(1L);
        verify(ingredientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetRecipesByIngredientName() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        List<Recipe> recipes = List.of(r1, r2);

        when(recipeRepository.findByAnyIngredientMatch(List.of("Tomato"))).thenReturn(recipes);

        List<Recipe> result = ingredientService.getRecipesByIngredientName("Tomato");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchIngredientsByName() {
        List<Ingredient> resultList = List.of(new Ingredient(1L, "Coriander"));

        when(ingredientRepository.findByNameContainingIgnoreCase("Cori")).thenReturn(resultList);

        List<Ingredient> result = ingredientService.searchIngredientsByName("Cori");
        assertEquals(1, result.size());
        assertEquals("Coriander", result.get(0).getName());
    }
}
