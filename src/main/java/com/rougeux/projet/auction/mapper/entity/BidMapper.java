package com.rougeux.projet.auction.mapper.entity;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.dto.BidDto;

public final class BidMapper {

    public static Bid clone(Bid source) {
        Bid bid = new Bid();
        bid.setId(source.getId());
        bid.setAmount(source.getAmount());
        bid.setTime(source.getTime());
        bid.setSlug(source.getSlug());
        bid.setUser((source.getUser() != null) ? UserMapper.clone(source.getUser()) : null);

        return bid;
    }

    public static BidDto toDto(Bid bid) {
        BidDto dto = new BidDto();
        dto.setAmount(bid.getAmount());
        dto.setTime(bid.getTime());
        dto.setUser(bid.getUser() != null ? UserMapper.toDtoSummary(bid.getUser()) : null);

        return dto;
    }
}
