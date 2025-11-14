package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public User getEntityByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void save(User user) {
        userDao.save(user);
    }
}
