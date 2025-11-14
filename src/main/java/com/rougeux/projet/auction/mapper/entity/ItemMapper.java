package com.rougeux.projet.auction.mapper.entity;

import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.dto.ItemDto;

public final class ItemMapper {

    public static Item clone(Item source) {
        Item item = new Item();
        item.setId(source.getId());
        item.setName(source.getName());
        item.setDescription(source.getDescription());
        item.setImage(source.getImage());
        item.setCategory((source.getCategory() != null) ? CategoryMapper.clone(source.getCategory()) : null);

        return item;
    }

    public static ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setImage(item.getImage());
        dto.setCategory(item.getCategory() != null ? CategoryMapper.toDto(item.getCategory()) : null);

        return dto;
    }
}
