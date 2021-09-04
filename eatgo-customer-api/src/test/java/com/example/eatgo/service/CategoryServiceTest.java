package com.example.eatgo.service;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup(){
        mockCategoryRepository();
    }

    private void mockCategoryRepository() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Korean Food").build());
        given(categoryRepository.findAll()).willReturn(categories);
    }

    @Test
    public void getCategories(){
        List<Category> categories = categoryService.getCategories();
        Category category = categories.get(0);
        assertThat(category.getName(), is("Korean Food"));
        verify(categoryRepository).findAll();
    }
}