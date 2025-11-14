package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IBidDao;
import com.rougeux.projet.auction.dto.request.BidRequest;
import com.rougeux.projet.auction.dto.response.BidResponse;
import com.rougeux.projet.auction.mapper.entity.SaleMapper;
import com.rougeux.projet.auction.mapper.entity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.rougeux.projet.auction.service.utils.CodeService.*;

@Service
public class BidService {

    private final IBidDao bidDao;
    private final UserService userService;

    public BidService(IBidDao bidDao, UserService userService) {
        this.bidDao = bidDao;
        this.userService = userService;
    }

    @Lazy
    private SaleService saleService;

    @Autowired
    public void setSaleService(@Lazy SaleService saleService) {
        this.saleService = saleService;
    }

    public List<Bid> findAllBySlug(String slug) {
        return bidDao.findBids(slug);
    }

    @Transactional
    public ApiResponse<BidResponse> placeBid(BidRequest bidRequest) {

        Sale sale = saleService.getEntityBySlug(bidRequest.getSlug());
        if(sale == null)
            return ApiResponseFactory.error(CD_ERR_NOTFOUND, "sales.findBy.error.notFound");

        if(sale.getEndAt().isBefore(LocalDateTime.now()))
            return ApiResponseFactory.error(CD_ERR_BID_SALE_CLOSED, "bid.error.sale.closed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal()))
            return ApiResponseFactory.error(CD_ERR_USER_AUTHENTICATED, "users.authenticated.error");

        User user = userService.getEntityByUsername(auth.getName());

        List<Bid> bids = findAllBySlugForUpdate(sale.getSlug());
        int currentPrice = bids.stream().mapToInt(Bid::getAmount).max().orElse(sale.getStartingPrice());

        if(bidRequest.getAmount() <= currentPrice)
            return ApiResponseFactory.error(CD_ERR_BID_AMOUNT_LOW, "bid.error.insufficientAmount");

        if(user.getCredit() < bidRequest.getAmount())
            return ApiResponseFactory.error(CD_ERR_BID_CREDIT_LOW, "bid.error.insufficientCredit");

        user.setCredit(user.getCredit() - bidRequest.getAmount());
        userService.save(user);

        Bid previousBid = bids.stream()
                .max(Comparator.comparingInt(Bid::getAmount))
                .orElse(null);

        if(previousBid != null) {
            User previousUser = userService.getEntityByUsername(previousBid.getUser().getUsername());
            previousUser.setCredit(previousUser.getCredit() + previousBid.getAmount());
            userService.save(previousUser);
        }

        Bid bid = new Bid();
        bid.setUser(user);
        bid.setAmount(bidRequest.getAmount());
        bid.setSlug(bidRequest.getSlug());
        bid.setTime(LocalDateTime.now());
        bidDao.save(bid);

        sale.setCurrentPrice(bid.getAmount());
        bids.add(0, bid);
        sale.setBids(bids);
        saleService.save(sale);

        return ApiResponseFactory.success(CD_OK_BID_SUBMIT, "bid.submit.success",
                new BidResponse(SaleMapper.toDto(sale), UserMapper.toDto(user)));
    }

    // TODO: lock mode en fonction du profil dao
    private List<Bid> findAllBySlugForUpdate(String slug) {
        return bidDao.findBids(slug);
    }
}
