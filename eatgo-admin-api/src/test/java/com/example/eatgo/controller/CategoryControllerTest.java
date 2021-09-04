package com.example.eatgo.controller;

import com.example.eatgo.domain.Category;
import com.example.eatgo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setup(){
        mockCategoryService();
    }

    private void mockCategoryService() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Korean Food").build());
        given(categoryService.getCategories()).willReturn(categories);
        given(categoryService.addCategory(any())).will(invocation -> {
            Category category = invocation.getArgument(0);
            return Category.builder().id(1L).name(category.getName()).build();
        });
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Korean Food")));

        verify(categoryService).getCategories();
    }

    @Test
    public void create() throws Exception{
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Korean Food\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/categories/1"))
                .andExpect(content().string("{}"));

        verify(categoryService).addCategory(any());
    }
}