package com.foodblog.backend.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import com.foodblog.backend.model.User;
import com.foodblog.backend.repository.UserRepository;


public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");   
        user.setEmail("email.com");   

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));           
        User foundUser = userService.getUserById(1L); 
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername()); 
        assertEquals("email.com", foundUser.getEmail()); 
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("newuser");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        verify(userRepository, times(1)).save(user); // verify that save was called exactly once
    }

    @Test
    public void testGetUserProfile() {
        User user = new User();
        user.setId(1L);
        user.setUsername("profileUser");
        user.setBio("Foodie");
        user.setName("Prisha");
        user.setProfilePicture("profile.jpg");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var profile = userService.getUserProfile(1L);

        assertNotNull(profile);
        assertEquals("profileUser", profile.getUsername());
        assertEquals("Prisha", profile.getName());
        assertEquals("Foodie", profile.getBio());
        assertEquals("profile.jpg", profile.getProfilePicture());
}

}