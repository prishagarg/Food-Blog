package com.foodblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String authorName;
    private int prepTimeMinutes;
    private int likeCount;
}