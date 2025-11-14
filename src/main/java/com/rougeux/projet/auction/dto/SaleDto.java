package com.rougeux.projet.auction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rougeux.projet.auction.dto.user.UserSummaryDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDto {

    private String slug;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int startingPrice;
    private int currentPrice;
    private int likes;
    private UserSummaryDto seller;
    private ItemDto item;
    private List<BidDto> bids;

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public LocalDateTime getStartAt() {
        return startAt;
    }
    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }
    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public int getStartingPrice() {
        return startingPrice;
    }
    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public UserSummaryDto getSeller() {
        return seller;
    }
    public void setSeller(UserSummaryDto seller) {
        this.seller = seller;
    }

    public ItemDto getItem() {
        return item;
    }
    public void setItem(ItemDto item) {
        this.item = item;
    }

    public List<BidDto> getBids() {
        return bids;
    }
    public void setBids(List<BidDto> bids) {
        this.bids = bids;
    }
}
