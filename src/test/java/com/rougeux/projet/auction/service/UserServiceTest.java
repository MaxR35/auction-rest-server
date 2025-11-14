package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import com.rougeux.projet.auction.dto.user.UserDto;
import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Profile("mock")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserDao userDao;

    @InjectMocks
    ApiResponseFactory apiResponseFactory;

    @Mock
    LocaleHelper localeHelper;

    @Test
    void findAllUsers() {
        User user = new User();

        when(userDao.findAll()).thenReturn(List.of(user));
        when(localeHelper.i18N("users.findAll.success")).thenReturn("Success message");
        ApiResponse<List<UserDto>> response = userService.getAllUsers();

        assertEquals("202", response.code());
    }

    @Test
    void getEntityByUsername_Success() {
        User user = new User();

        when(userDao.findByUsername("username")).thenReturn(user);
        assertNotNull(userService.getEntityByUsername("username"));
    }

    @Test
    void save() {
        User user = new User();
        userService.save(user);

        verify(userDao).save(any(User.class));
    }
}
