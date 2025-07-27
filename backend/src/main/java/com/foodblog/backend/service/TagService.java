package com.foodblog.backend.service;

import java.util.List;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.Tag;

public interface TagService{
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagById(Long id);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    List<Tag> searchTagsByName(String name);
    List<Recipe> getRecipesByTagName(String name);
    Tag findOrCreateByNormalizedName(String name);
}