package com.rougeux.projet.auction.dto.response;

import com.rougeux.projet.auction.dto.SaleDto;
import com.rougeux.projet.auction.dto.user.UserDto;

public class BidResponse {

    private SaleDto sale;
    private UserDto user;

    public BidResponse(SaleDto sale, UserDto user) {
        this.sale = sale;
        this.user = user;
    }

    public SaleDto getSale() {
        return sale;
    }
    public void setSale(SaleDto sale) {
        this.sale = sale;
    }

    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
}
