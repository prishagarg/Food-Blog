package com.foodblog.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodblog.backend.model.Ingredient;
import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.service.IngredientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Tomato");

        when(ingredientService.createIngredient(any(Ingredient.class))).thenReturn(ingredient);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    void testGetAllIngredients() throws Exception {
        Ingredient ing1 = new Ingredient(1L, "Onion");
        Ingredient ing2 = new Ingredient(2L, "Garlic");

        when(ingredientService.getAllIngredients()).thenReturn(List.of(ing1, ing2));

        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient(1L, "Salt");

        when(ingredientService.getIngredientById(1L)).thenReturn(ingredient);

        mockMvc.perform(get("/api/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Salt"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        Ingredient updated = new Ingredient(1L, "Chili");

        when(ingredientService.updateIngredient(eq(1L), any(Ingredient.class))).thenReturn(updated);

        mockMvc.perform(put("/api/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chili"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        doNothing().when(ingredientService).deleteIngredient(1L);

        mockMvc.perform(delete("/api/ingredients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchIngredientsByName() throws Exception {
        Ingredient ing = new Ingredient(1L, "Sugar");
        when(ingredientService.searchIngredientsByName("sug")).thenReturn(List.of(ing));

        mockMvc.perform(get("/api/ingredients/search?name=sug"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Sugar"));
    }

    @Test
    void testGetRecipesByIngredientName() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Pasta");

        when(ingredientService.getRecipesByIngredientName("Tomato")).thenReturn(List.of(recipe));

        mockMvc.perform(get("/api/ingredients/recipes?name=Tomato"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Pasta"));
    }
}
