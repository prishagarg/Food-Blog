package com.foodblog.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.foodblog.backend.dto.UserProfileDTO;
import com.foodblog.backend.dto.UserSummaryDTO;
import com.foodblog.backend.model.User;
import com.foodblog.backend.repository.UserRepository;
import com.foodblog.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserProfileDTO getUserProfile(Long id) {
        User user = getUserById(id);
        if (user == null) {
            return null;
        }
        
        List<UserSummaryDTO> followersDTO = user.getFollowers().stream()
                .map(follower -> new UserSummaryDTO(follower.getId(), follower.getUsername(), follower.getName()))
                .collect(Collectors.toList());
        
        List<UserSummaryDTO> followingDTO = user.getFollowing().stream()
                .map(following -> new UserSummaryDTO(following.getId(), following.getUsername(), following.getName()))
                .collect(Collectors.toList());

        return new UserProfileDTO(user.getId(), user.getUsername(), user.getName(), user.getBio(), user.getProfilePicture(), followersDTO, followingDTO);
    }
}