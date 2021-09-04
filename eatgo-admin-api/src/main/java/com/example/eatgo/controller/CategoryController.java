package com.example.eatgo.controller;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.Region;
import com.example.eatgo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list(){
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    public ResponseEntity<?> create(@RequestBody Category resource) throws URISyntaxException {
        Category category = categoryService.addCategory(resource);
        URI uri = new URI("/api/categories/" + category.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
