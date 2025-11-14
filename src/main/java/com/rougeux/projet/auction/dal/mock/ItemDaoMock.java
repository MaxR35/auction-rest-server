package com.rougeux.projet.auction.dal.mock;

import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.dal.IItemDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mock")
public class ItemDaoMock implements IItemDao {

    private static final List<Item> LST_ITEM = new ArrayList<>();

    @Override
    public List<Item> findAll() { return LST_ITEM; }

    @Override
    public void save(Item item) { LST_ITEM.add(item); }

    @Override
    public Integer checkData() { return LST_ITEM.size(); }
}
