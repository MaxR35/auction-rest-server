package com.rougeux.projet.auction.dal.mongo.repository;

import com.rougeux.projet.auction.bo.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {}
