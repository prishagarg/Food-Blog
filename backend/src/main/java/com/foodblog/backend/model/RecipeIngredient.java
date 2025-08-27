package com.foodblog.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;
    
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    @JsonIgnore
    private Ingredient ingredient;
    
    @Column(nullable = false)
    private String quantity; // "2", "1.5", "1/4"
    
    @Column
    private String unit; // "cups", "tbsp", "pieces", "tsp"

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (this.unit != null) {
            this.unit = this.unit.trim().toLowerCase();
        }
    }

}
