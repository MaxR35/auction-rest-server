package com.rougeux.projet.auction.dal;

import com.rougeux.projet.auction.bo.Sale;

import java.util.List;

public interface ISaleDao {

    List<Sale> findAll();
    List<Sale> findAllByEndAt();
    Sale findById(String itemSlug);
    void save(Sale sale);

    Integer checkData();
}
