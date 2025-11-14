package com.rougeux.projet.auction.dal.mock;

import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.dal.ISaleDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("mock")
public class SaleDaoMock implements ISaleDao {

    private static final List<Sale> LST_SALE = new ArrayList<>();

    @Override
    public List<Sale> findAll() {
        return LST_SALE;
    }

    @Override
    public List<Sale> findAllByEndAt() {
        return LST_SALE.stream()
                .filter(sale -> sale.getEndAt().isBefore(LocalDateTime.now()))
                .toList();
    }

    @Override
    public Sale findById(String id) {
        return LST_SALE.stream()
                .filter(sale -> sale.getSlug().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Sale sale) {
        for (int i = 0; i < LST_SALE.size(); i++) {
            if (LST_SALE.get(i).getId().equals(sale.getId())) {
                LST_SALE.set(i, sale);
                return;
            }
        }
        sale.setId(UUID.randomUUID().toString());
        LST_SALE.add(sale);
    }

    @Override
    public Integer checkData() { return LST_SALE.size(); }
}
