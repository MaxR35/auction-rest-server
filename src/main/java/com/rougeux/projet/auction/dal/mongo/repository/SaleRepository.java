package com.rougeux.projet.auction.dal.mongo.repository;

import com.rougeux.projet.auction.bo.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends MongoRepository<Sale, String> {

    List<Sale> findByEndAtBefore(LocalDateTime now);
    Optional<Sale> findBySlug(String slug);
}
