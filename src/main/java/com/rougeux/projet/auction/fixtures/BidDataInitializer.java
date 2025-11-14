package com.rougeux.projet.auction.fixtures;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IBidDao;
import com.rougeux.projet.auction.dal.ISaleDao;
import com.rougeux.projet.auction.dal.IUserDao;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Order(5)
@Component
public class BidDataInitializer implements CommandLineRunner {

    private final Faker faker = new Faker();
    private final IUserDao userDao;
    private final ISaleDao saleDao;
    private final IBidDao bidDao;

    public BidDataInitializer(IUserDao userDao, ISaleDao saleDao, IBidDao bidDao) {
        this.userDao = userDao;
        this.saleDao = saleDao;
        this.bidDao = bidDao;
    }

    @Override
    public void run(String... args) throws Exception {
        if(bidDao.checkData() > 0) {
            return;
        }
        List<User> users = userDao.findAll();

        for(Sale sale : saleDao.findAll()) {
            int currentPrice = sale.getStartingPrice();

            for (int i = 0; i < faker.number().numberBetween(0, 20); i++) {
                currentPrice = currentPrice + faker.number().numberBetween(1, 10);

                Bid bid = new Bid();

                bid.setId(null);
                bid.setAmount(currentPrice);
                bid.setTime(LocalDateTime.now().minusMinutes(120 - ((long) i * faker.number().numberBetween(1, 10))));
                bid.setSlug(sale.getSlug());

                List<User> possibleBidders = users.stream()
                        .filter(u -> !u.equals(sale.getSeller()))
                        .toList();
                User user = possibleBidders.get(faker.number().numberBetween(0, possibleBidders.size()));
                bid.setUser(user);

                bidDao.save(bid);
            }
            sale.setCurrentPrice(currentPrice);
            saleDao.save(sale);
        }
    }
}
