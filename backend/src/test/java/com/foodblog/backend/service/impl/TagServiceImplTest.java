package com.foodblog.backend.service.impl;

import com.foodblog.backend.model.Tag;
import com.foodblog.backend.repository.TagRepository;
import com.foodblog.backend.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTag_shouldSaveAndReturnTag() {
        Tag tag = new Tag();
        tag.setName("spicy");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag saved = tagService.createTag(tag);

        assertEquals("spicy", saved.getName());
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void getTagById_shouldReturnTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("vegan");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        Tag found = tagService.getTagById(1L);

        assertNotNull(found);
        assertEquals("vegan", found.getName());
    }

    @Test
    void deleteTag_shouldCallDeleteById() {
        Long tagId = 2L;
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName("dessert");
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        tagService.deleteTag(tagId);
        verify(tagRepository).deleteById(tagId);
    }
}
