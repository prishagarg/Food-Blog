package com.foodblog.backend.dto;

import java.util.List;

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
    private List<UserSummaryDTO> followers;
    private List<UserSummaryDTO> following;
   
}
