package com.rougeux.projet.auction.dal.mongo;

import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.dal.IItemDao;
import com.rougeux.projet.auction.dal.mongo.repository.ItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("mongo")
public class ItemDaoMongo implements IItemDao {

    private final ItemRepository itemRepository;

    public ItemDaoMongo(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Integer checkData() { return Math.toIntExact(itemRepository.count()); }
}
