package com.rougeux.projet.auction.dal.mongo.repository;

import com.rougeux.projet.auction.bo.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {}
