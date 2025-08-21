package com.foodblog.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.foodblog.backend.dto.RecipeDTO;
import com.foodblog.backend.model.Tag;

public interface TagService{
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
    Optional<Tag> getTagById(Long id);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    List<Tag> searchTagsByName(String name);
    Page<RecipeDTO> getRecipesByTagName(String name);
    Tag findOrCreateByNormalizedName(String name);
    boolean existsByName(String name);
}