package com.foodblog.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;   
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String title;

    @Column(length=1000)
    private String description;
    
    private String cuisine;
    private int servings;
    private int prepTimeMinutes;
    private int cookingTimeMinutes;
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "recipe_tags",
        joinColumns=@JoinColumn(name="recipe_id"),
        inverseJoinColumns=@JoinColumn(name="tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ElementCollection
    private List<String> steps;

    @ManyToMany(mappedBy = "likedRecipes")
    @JsonIgnore
    private Set<User> likedBy = new HashSet<>();

    private LocalDateTime updatedAt;
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToMany(mappedBy = "savedRecipes")
    @JsonIgnore
    private Set<User> savedBy = new HashSet<>();

    public void addTag(Tag tag) {
        if (tag == null) return;
        if(this.tags.add(tag)){
            tag.getRecipes().add(this);
        }
    }

    public void removeTag(Tag tag) {
        if (tag == null) return;
        if(this.tags.remove(tag)){
            tag.getRecipes().remove(this);
        }
    }




    
}

