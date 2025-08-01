package com.foodblog.backend.service.impl;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.Tag;
import com.foodblog.backend.repository.TagRepository;
import com.foodblog.backend.repository.RecipeRepository;
import com.foodblog.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Tag createTag(Tag tag) {
        tag.setName(tag.getName().trim().toLowerCase());
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        if (existingTag.isPresent()) {
            return existingTag.get();
        }
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = tagRepository.findById(id).orElse(null);
        if (existingTag != null) {
            existingTag.setName(tag.getName().trim().toLowerCase());
            return tagRepository.save(existingTag);
        }
        return null;
    }

    @Override 
    public void deleteTag(Long id){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + id));
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> searchTagsByName(String name){
        return tagRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Override
    public List<Recipe> getRecipesByTagName(String name){
        return recipeRepository.findByTags_NameIgnoreCase(name.trim().toLowerCase());
    }

    @Override
    public Tag findOrCreateByNormalizedName(String name) {
        String normalized = name.trim().toLowerCase();
        return tagRepository.findByName(normalized)
                .orElseGet(() -> tagRepository.save(new Tag(null, normalized)));
    }
}