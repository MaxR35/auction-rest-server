package com.rougeux.projet.auction.dal;

import com.rougeux.projet.auction.bo.Category;

import java.util.List;

public interface ICategoryDao {

    List<Category> findAll();
    void save(Category category);

    Integer checkData();
}
