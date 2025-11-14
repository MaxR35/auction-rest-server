package com.rougeux.projet.auction.mapper.entity;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dto.CategoryDto;

public final class CategoryMapper {

    public static Category clone(Category source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setSlug(source.getSlug());
        category.setLabel(source.getLabel());

        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setSlug(category.getSlug());
        dto.setLabel(category.getLabel());

        return dto;
    }
}
