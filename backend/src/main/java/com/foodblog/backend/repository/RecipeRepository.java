package com.foodblog.backend.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.Tag;
import com.foodblog.backend.model.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    // Find user's recipes
    Page<Recipe> findByCreatedBy(User createdBy);
    
    // Search recipes by title
    Page<Recipe> findByTitleContainingIgnoreCase(String title);
    
    // Get recent recipes (paginated)
    Page<Recipe> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    // Find by cuisine (paginated)
    Page<Recipe> findByCuisine(String cuisine, Pageable pageable);
    
    // Find by tag (paginated)
    Page<Recipe> findByTagsContaining(Tag tag, Pageable pageable);

    // Find recipes liked by a user (for user's liked recipes page)
    Page<Recipe> findByLikedByContaining(User user);

    // Find recipes saved by a user (for user's saved recipes page) 
    Page<Recipe> findBySavedByContaining(User user);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.recipeIngredients ri WHERE ri.ingredient.name ILIKE %:ingredientName%")
    Page<Recipe> findByIngredientNameContainingIgnoreCase(@Param("ingredientName") String ingredientName, Pageable pageable);
}