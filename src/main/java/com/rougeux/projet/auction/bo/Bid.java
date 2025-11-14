package com.rougeux.projet.auction.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bids")
public class Bid {

    @Id
    private String id;
    private int amount;
    private LocalDateTime time;
    private String slug;

    @DBRef
    private User user;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSlug() {
        return slug;
    }
    public void setSlug(String itemSlug) {
        this.slug = itemSlug;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}