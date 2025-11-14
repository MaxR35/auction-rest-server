package com.rougeux.projet.auction.dal;

import com.rougeux.projet.auction.bo.Item;

import java.util.List;

public interface IItemDao {

    List<Item> findAll();
    void save(Item item);

    Integer checkData();
}
