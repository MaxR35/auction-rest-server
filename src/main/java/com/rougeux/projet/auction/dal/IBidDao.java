package com.rougeux.projet.auction.dal;

import com.rougeux.projet.auction.bo.Bid;

import java.util.List;

public interface IBidDao {

    List<Bid> findBids(String itemSlug);
    Bid getByUserId(String userId);
    void save(Bid bid);

    Integer checkData();
}
