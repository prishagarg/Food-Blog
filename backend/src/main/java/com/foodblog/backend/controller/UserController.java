package com.foodblog.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodblog.backend.dto.UserProfileDTO;
import com.foodblog.backend.dto.UserSummaryDTO;
import com.foodblog.backend.model.User;
import com.foodblog.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createdUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @GetMapping("/{id}/profile")
    public UserProfileDTO getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @GetMapping("/username/{username}/profile")
    public UserProfileDTO getUserProfileByUsername(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);  
    }
    @PutMapping("/{followerId}/follow/{followingId}")
    public void followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        userService.followUser(followerId, followingId);
    }
    
    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public void unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        userService.unfollowUser(followerId, followingId);
        
    }

    @GetMapping("/{id}/followers")
    public List<UserSummaryDTO> getFollowers(@PathVariable Long id) {
        return userService.getFollowers(id);
    }

    @GetMapping("/{id}/following")
    public List<UserSummaryDTO> getFollowing(@PathVariable Long id) {
        return userService.getFollowing(id);
    }
} 