package com.rougeux.projet.auction.dal.mongo.repository;

import com.rougeux.projet.auction.bo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findBySlug(String slug);
}
