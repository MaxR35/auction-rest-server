package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dal.ICategoryDao;
import com.rougeux.projet.auction.dto.CategoryDto;
import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    ICategoryDao categoryDao;

    @InjectMocks
    ApiResponseFactory apiResponseFactory;

    @Mock
    private LocaleHelper localeHelper;

    @Test
    void findAllCategories() {
        Category category = new Category();

        when(categoryDao.findAll()).thenReturn(List.of(category));
        when(localeHelper.i18N("categories.findAll.success")).thenReturn("Success message");
        ApiResponse<List<CategoryDto>> response = categoryService.getAllCategories();

        assertEquals("202", response.code());
    }
}
