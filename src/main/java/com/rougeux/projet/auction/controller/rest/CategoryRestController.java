package com.rougeux.projet.auction.controller.rest;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.dto.CategoryDto;
import com.rougeux.projet.auction.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
