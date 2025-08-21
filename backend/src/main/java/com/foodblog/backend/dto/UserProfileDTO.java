package com.foodblog.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String username;
    private String name;
    private String bio;
    private String profilePicture;
    private int followerCount;
    private int followingCount;
    private int recipeCount;
    private boolean isFollowing; 
   
}
