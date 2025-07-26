package com.foodblog.backend.service;

import java.util.List;

import com.foodblog.backend.dto.UserProfileDTO;
import com.foodblog.backend.dto.UserSummaryDTO;
import com.foodblog.backend.model.User;

public interface UserService{
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    UserProfileDTO getUserProfile(Long id);
    User getUserByUsername(String username);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    UserProfileDTO getUserProfileByUsername(String username);
    List<UserSummaryDTO> searchUsersByUsername(String keyword);
    void followUser(Long followerId, Long followingId);
    void unfollowUser(Long followerId, Long followingId);
    List<UserSummaryDTO> getFollowers(Long userId);
    List<UserSummaryDTO> getFollowing(Long userId); 
    boolean isFollowing(Long followerId, Long followingId);
}