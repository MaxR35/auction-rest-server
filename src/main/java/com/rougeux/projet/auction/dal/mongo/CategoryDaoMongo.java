package com.rougeux.projet.auction.dal.mongo;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dal.ICategoryDao;
import com.rougeux.projet.auction.dal.mongo.repository.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("mongo")
public class CategoryDaoMongo implements ICategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryDaoMongo(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Integer checkData() { return Math.toIntExact(categoryRepository.count()); }
}
