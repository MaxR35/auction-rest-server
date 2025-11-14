package com.rougeux.projet.auction.dal.mongo;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.dal.IBidDao;
import com.rougeux.projet.auction.dal.mongo.repository.BidRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("mongo")
public class BidDaoMongo implements IBidDao {

    private final BidRepository bidRepository;

    public BidDaoMongo(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public List<Bid> findBids(String slug) {
        return bidRepository.findAllBySlugOrderByAmountDesc(slug);
    }

    @Override
    public Bid getByUserId(String userId) {
        return bidRepository.findByUser_IdOrderByAmountDesc(userId).orElse(null);
    }

    @Override
    public void save(Bid bid) {
        bidRepository.save(bid);
    }

    @Override
    public Integer checkData() {
        return Math.toIntExact(bidRepository.count());
    }
}
