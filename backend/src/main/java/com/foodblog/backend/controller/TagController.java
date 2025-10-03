package com.foodblog.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodblog.backend.model.Recipe;
import com.foodblog.backend.model.Tag;
import com.foodblog.backend.service.TagService;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*") // Optional: adjust for your frontend origin
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Create a tag
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok(createdTag);
    }

    // Get all tags
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    // Get tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tag>> getTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.getTagById(id);
        if (tag == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tag);
    }

    // Update tag
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        Tag updated = tagService.updateTag(id, tag);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // Delete tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    // Search tags by name (e.g., partial match)
    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String name) {
        return ResponseEntity.ok(tagService.searchTagsByName(name));
    }

    // Get recipes by tag name
    @GetMapping("/{name}/recipes")
    public ResponseEntity<Page<Recipe>> getRecipesByTag(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(tagService.getRecipesByTag(name, pageable));
    }
}
