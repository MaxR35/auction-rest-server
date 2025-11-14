package com.rougeux.projet.auction.dal.mock;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("mock")
public class UserDaoMock implements IUserDao {

    private static final List<User> LST_USER = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return LST_USER;
    }

    @Override
    public User findByUsername(String username) {
        return LST_USER.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public User findBySlug(String slug) {
        return LST_USER.stream()
                .filter(u -> u.getSlug().equals(slug))
                .findFirst().orElse(null);
    }

    @Override
    public void save(User user) {
        for (int i = 0; i < LST_USER.size(); i++) {
            if (LST_USER.get(i).getId().equals(user.getId())) {
                LST_USER.set(i, user);
                return;
            }
        }
        user.setId(UUID.randomUUID().toString());
        LST_USER.add(user);
    }

    @Override
    public Integer checkData() { return LST_USER.size(); }
}
