package com.rougeux.projet.auction.mapper.entity;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dto.user.UserDto;
import com.rougeux.projet.auction.dto.user.UserSummaryDto;

public final class UserMapper {

    public static User clone(User source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setSlug(source.getSlug());
        user.setFirstname(source.getFirstname());
        user.setLastname(source.getLastname());
        user.setPhone(source.getPhone());
        user.setCredit(source.getCredit());
        user.setImage(source.getImage());
        user.setAdmin(source.isAdmin());
        user.setCreateAt(source.getCreateAt());

        return user;
    }

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setSlug(user.getSlug());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setImage(user.getImage());
        dto.setPhone(user.getPhone());
        dto.setCredit(user.getCredit());
        dto.setCreateAt(user.getCreateAt());

        return dto;
    }

    public static UserSummaryDto toDtoSummary(User user) {
        UserSummaryDto dto = new UserSummaryDto();
        dto.setSlug(user.getSlug());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setImage(user.getImage());
        dto.setCreateAt(user.getCreateAt());

        return dto;
    }
}
