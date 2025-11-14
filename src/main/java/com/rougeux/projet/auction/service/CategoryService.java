package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.dal.ICategoryDao;
import com.rougeux.projet.auction.dto.CategoryDto;
import com.rougeux.projet.auction.mapper.entity.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rougeux.projet.auction.service.utils.CodeService.CD_DATA_SUCCESS;

@Service
public class CategoryService {

    private final ICategoryDao categoryDao;

    public CategoryService(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public ApiResponse<List<CategoryDto>> getAllCategories() {
        return ApiResponseFactory.success(CD_DATA_SUCCESS, "categories.findAll.success",
                categoryDao.findAll().stream().map(CategoryMapper::toDto).toList());
    }
}
