package com.rougeux.projet.auction.dal.mongo;

import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.dal.ISaleDao;
import com.rougeux.projet.auction.dal.mongo.repository.SaleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("mongo")
public class SaleDaoMongo implements ISaleDao {

    private final SaleRepository saleRepository;

    public SaleDaoMongo(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> findAllByEndAt() {
        return saleRepository.findByEndAtBefore(LocalDateTime.now());
    }

    @Override
    public Sale findById(String slug) {
        return saleRepository.findBySlug(slug).orElse(null);
    }

    @Override
    public void save(Sale sale) {
        saleRepository.save(sale);
    }

    @Override
    public Integer checkData() { return Math.toIntExact(saleRepository.count()); }
}
