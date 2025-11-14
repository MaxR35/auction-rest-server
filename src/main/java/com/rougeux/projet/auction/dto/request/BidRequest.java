package com.rougeux.projet.auction.dto.request;

public class BidRequest {

    private int amount;
    private String slug;

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
}
