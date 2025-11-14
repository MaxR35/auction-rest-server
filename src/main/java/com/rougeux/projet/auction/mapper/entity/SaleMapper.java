package com.rougeux.projet.auction.mapper.entity;

import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.dto.SaleDto;

public final class SaleMapper {

    public static Sale clone(Sale source) {
        Sale sale = new Sale();
        sale.setId(source.getId());
        sale.setSlug(source.getSlug());
        sale.setCreateAt(source.getCreateAt());
        sale.setStartAt(source.getStartAt());
        sale.setEndAt(source.getEndAt());
        sale.setStartingPrice(source.getStartingPrice());
        sale.setCurrentPrice(source.getCurrentPrice());
        sale.setLikes(source.getLikes());
        sale.setClosed(source.isClosed());
        sale.setSeller((source.getSeller() != null) ? UserMapper.clone(source.getSeller()) : null);
        sale.setItem((source.getItem() != null) ? ItemMapper.clone(source.getItem()) : null);
        sale.setBids((source.getBids() != null) ? source.getBids().stream().map(BidMapper::clone).toList() : null);

        return sale;
    }

    public static SaleDto toDto(Sale sale) {
        SaleDto dto = new SaleDto();
        dto.setSlug(sale.getSlug());
        dto.setStartAt((sale.getStartAt()));
        dto.setEndAt((sale.getEndAt()));
        dto.setStartingPrice((sale.getStartingPrice()));
        dto.setCurrentPrice((sale.getCurrentPrice()));
        dto.setLikes(sale.getLikes());

        dto.setSeller(sale.getSeller() != null ? UserMapper.toDtoSummary(sale.getSeller()) : null);
        dto.setItem(sale.getItem() != null ? ItemMapper.toDto(sale.getItem()) : null);
        dto.setBids((sale.getBids() != null) ? sale.getBids().stream().map(BidMapper::toDto).toList() : null);

        return dto;
    }
}
