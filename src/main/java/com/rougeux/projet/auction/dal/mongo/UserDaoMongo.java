package com.rougeux.projet.auction.dal.mongo;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import com.rougeux.projet.auction.dal.mongo.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("mongo")
public class UserDaoMongo implements IUserDao {

    private final UserRepository userRepository;

    public UserDaoMongo(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public List<User> findAll() { return userRepository.findAll(); }

    @Override
    public User findByUsername(String username) { return userRepository.findByUsername(username).orElse(null); }

    @Override
    public User findBySlug(String slug) { return userRepository.findBySlug(slug).orElse(null); }

    @Override
    public void save(User user) { userRepository.save(user); }

    @Override
    public Integer checkData() { return Math.toIntExact(userRepository.count()); }
}
