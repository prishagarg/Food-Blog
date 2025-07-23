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

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        if (existingUser == null) {
            return null;
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());
        existingUser.setBio(user.getBio());
        existingUser.setProfilePicture(user.getProfilePicture());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserProfileDTO getUserProfileByUsername(String username) {
        User user = getUserByUsername(username);
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

    @Override
    public void followUser(Long followerId, Long followingId) {
        User follower = getUserById(followerId);
        User following = getUserById(followingId);
        if (follower != null && following != null && !follower.getFollowing().contains(following)) {
            follower.getFollowing().add(following);
            following.getFollowers().add(follower);
            userRepository.save(follower);
            userRepository.save(following);
        }
    }

    @Override
    public void unfollowUser(Long followerId, Long followingId) {
        User follower = getUserById(followerId);
        User following = getUserById(followingId);
        if (follower != null && following != null) {
            follower.getFollowing().remove(following);
            following.getFollowers().remove(follower);
            userRepository.save(follower);
            userRepository.save(following);
        }
    }

    @Override
    public List<UserSummaryDTO> getFollowers(Long userId) {
        User user = getUserById(userId);
        if(user==null) return null;
        return user.getFollowers().stream()
                .map(follower -> new UserSummaryDTO(follower.getId(), follower.getUsername(), follower.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSummaryDTO> getFollowing(Long userId) {
        User user = getUserById(userId);
        if(user==null) return null;
        return user.getFollowing().stream()
                    .map(following -> new UserSummaryDTO(following.getId(), following.getUsername(), following.getName()))
                    .collect(Collectors.toList());
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        User follower = getUserById(followerId);
        User following = getUserById(followingId);
        return follower.getFollowing().contains(following);
    }

}