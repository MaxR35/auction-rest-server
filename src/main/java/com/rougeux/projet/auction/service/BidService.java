package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.dal.IBidDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    private final IBidDao bidDao;

    public BidService(IBidDao bidDao) {
        this.bidDao = bidDao;
    }

    public List<Bid> findAllBySlug(String slug) {
        return bidDao.findBids(slug);
    }
}
