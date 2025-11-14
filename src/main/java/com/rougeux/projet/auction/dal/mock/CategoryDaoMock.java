package com.rougeux.projet.auction.dal.mock;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dal.ICategoryDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mock")
public class CategoryDaoMock implements ICategoryDao {

    private static final List<Category> LST_CATEGORY = new ArrayList<>();

    @Override
    public List<Category> findAll() { return LST_CATEGORY; }

    @Override
    public void save(Category category) { LST_CATEGORY.add(category); }

    @Override
    public Integer checkData() { return LST_CATEGORY.size(); }
}
