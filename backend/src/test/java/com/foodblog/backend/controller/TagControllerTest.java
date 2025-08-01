package com.foodblog.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodblog.backend.model.Tag;
import com.foodblog.backend.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTags() throws Exception {
        List<Tag> tags = Arrays.asList(new Tag(1L, "vegan"), new Tag(2L, "spicy"));
        when(tagService.getAllTags()).thenReturn(tags);

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tags.size()));
    }

    @Test
    void testCreateTag() throws Exception {
        Tag tag = new Tag(null, "newtag");
        Tag savedTag = new Tag(1L, "newtag");

        when(tagService.createTag(any(Tag.class))).thenReturn(savedTag);

        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("newtag"));
    }
}
