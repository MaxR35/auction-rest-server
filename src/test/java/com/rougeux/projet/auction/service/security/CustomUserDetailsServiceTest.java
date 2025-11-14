package com.rougeux.projet.auction.service.security;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@Profile("mock")
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    IUserDao userDao;

    @Test
    void loadUser_Success() {
        User user = new User();
        user.setUsername("username");

        when(userDao.findByUsername("username")).thenReturn(user);

        UserDetails result = customUserDetailsService.loadUserByUsername("username");
        assertEquals("username", result.getUsername());
    }

    @Test
    void loadUser_Error() {
        when(userDao.findByUsername("username")).thenReturn(null);
        assertNull(customUserDetailsService.loadUserByUsername("username"));
    }
}
