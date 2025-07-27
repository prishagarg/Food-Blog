package com.foodblog.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foodblog.backend.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTags_NameIgnoreCase(String name);
    List<Recipe> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE i.name IN :ingredientNames")
    List<Recipe> findByAnyIngredientMatch(@Param("ingredientNames") List<String> ingredientNames);
    @Query("""
        SELECT r 
        FROM Recipe r 
        JOIN r.ingredients i 
        WHERE i.name IN :ingredientNames 
        GROUP BY r.id 
        HAVING COUNT(DISTINCT i.name) = :ingredientCount
    """)
    List<Recipe> findByAllIngredientMatch(
        @Param("ingredientNames") List<String> ingredientNames,
        @Param("ingredientCount") long ingredientCount
    );

}