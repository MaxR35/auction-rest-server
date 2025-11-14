package com.rougeux.projet.auction.dal.mongo.repository;

import com.rougeux.projet.auction.bo.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends MongoRepository<Bid, String> {


    List<Bid> findAllBySlugOrderByAmountDesc(String slug);
    Optional<Bid> findByUser_IdOrderByAmountDesc(String id);
}
