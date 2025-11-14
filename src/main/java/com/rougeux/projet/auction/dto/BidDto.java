package com.rougeux.projet.auction.dto;

import com.rougeux.projet.auction.dto.user.UserSummaryDto;

import java.time.LocalDateTime;

public class BidDto {

    private int amount;
    private LocalDateTime time;
    private UserSummaryDto user;

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

    public UserSummaryDto getUser() {
        return user;
    }
    public void setUser(UserSummaryDto user) {
        this.user = user;
    }
}
