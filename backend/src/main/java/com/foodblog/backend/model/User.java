package com.foodblog.backend.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Email(message = "Please provide a valid email address")
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String name;
    private String bio;
    private String profilePicture;
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    @ManyToMany
    @JoinTable(
        name = "user_following",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following = new HashSet<>();
    @ManyToMany(mappedBy="following")
    private Set<User> followers = new HashSet<>();


    @ManyToMany
    @JoinTable(
        name = "user_liked_recipes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> likedRecipes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name= "user_saved_recipes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> savedRecipes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public void follow(User userToFollow) {
        if (userToFollow == null || this.equals(userToFollow)) {
            return; // Optional: prevent self-follow or null
        }
        this.following.add(userToFollow);
        userToFollow.getFollowers().add(this);
    }

    // Unfollow another user
    public void unfollow(User userToUnfollow) {
        if (userToUnfollow == null) return;
        this.following.remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(this);
    }

    // Like a recipe
    public void likeRecipe(Recipe recipe) {
        if (recipe == null) return;
        this.likedRecipes.add(recipe);
        recipe.getLikedBy().add(this);
    }

    // Unlike a recipe
    public void unlikeRecipe(Recipe recipe) {
        if (recipe == null) return;
        this.likedRecipes.remove(recipe);
        recipe.getLikedBy().remove(this);
    }

    public void addSave(Recipe recipe) {
        if (recipe == null) return;
        if (this.savedRecipes.add(recipe)) {
            recipe.getSavedBy().add(this);
        }
    }

    public void removeSave(Recipe recipe) {
        if (recipe == null) return;
        if (this.savedRecipes.remove(recipe)) {
            recipe.getSavedBy().remove(this);
        }
    }
}