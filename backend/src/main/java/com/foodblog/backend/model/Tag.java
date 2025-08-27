package com.foodblog.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @PrePersist
    @PreUpdate
    public void normalize() {
        if(this.name != null) {
            this.name = this.name.trim().toLowerCase();
        }
    }

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore // avoids infinite loop when serializing Recipe <-> Tag
    private Set<Recipe> recipes = new HashSet<>();


    public void addRecipe(Recipe recipe) {
        if(recipe==null) return;
        if(this.recipes.add(recipe)) {
            recipe.getTags().add(this);
        }
    }

    public void removeRecipe(Recipe recipe) {
        if(recipe==null) return;
        if(this.recipes.remove(recipe)) {
            recipe.getTags().remove(this);
        }
    }
}