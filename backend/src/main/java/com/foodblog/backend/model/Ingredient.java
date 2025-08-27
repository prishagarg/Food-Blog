package com.foodblog.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient == null) return;
        this.recipeIngredients.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
    }

    public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient == null) return;
        this.recipeIngredients.remove(recipeIngredient);
        recipeIngredient.setIngredient(null);
    }

}