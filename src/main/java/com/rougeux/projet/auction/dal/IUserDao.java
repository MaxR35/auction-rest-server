package com.rougeux.projet.auction.dal;

import com.rougeux.projet.auction.bo.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll();
    User findByUsername(String username);
    User findBySlug(String slug);
    void save(User user);

    Integer checkData();
}
