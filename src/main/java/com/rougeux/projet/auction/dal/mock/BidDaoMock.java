package com.rougeux.projet.auction.dal.mock;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.dal.IBidDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("mock")
public class BidDaoMock implements IBidDao {

    private static final List<Bid> LST_BID = new ArrayList<>();

    @Override
    public List<Bid> findBids(String itemSlug) {
        return LST_BID.stream()
                .filter(bid -> bid.getSlug().equals(itemSlug))
                .sorted(Comparator.comparing(Bid::getAmount).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Bid getByUserId(String userId) {
        return LST_BID.stream()
                .filter(bid -> bid.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Bid bid) {
        for (int i = 0; i < LST_BID.size(); i++) {
            if(LST_BID.get(i).getId().equals(bid.getId())) {
                LST_BID.set(i, bid);
            }
        }
        bid.setId(UUID.randomUUID().toString());
        LST_BID.add(bid);
    }

    @Override
    public Integer checkData() {
        return LST_BID.size();
    }
}
