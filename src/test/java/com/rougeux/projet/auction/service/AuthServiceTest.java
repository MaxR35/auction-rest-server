package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.dto.request.LoginRequest;
import com.rougeux.projet.auction.dto.response.LoginResponse;
import com.rougeux.projet.auction.service.security.CustomUserDetailsService;
import com.rougeux.projet.auction.service.security.JwtService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LocaleHelper localeHelper;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_Success() {
        UserDetails userDetails = mock(UserDetails.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password123");

        when(customUserDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        ApiResponse<LoginResponse> response = authService.login(loginRequest);

        assertEquals("201", response.code());
    }

    @Test
    void login_InvalidUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user1");

        when(customUserDetailsService.loadUserByUsername("user1")).thenReturn(null);

        ApiResponse<LoginResponse> response = authService.login(loginRequest);

        assertEquals("101", response.code());
    }

    @Test
    void login_InvalidPassword() {
        UserDetails userDetails = mock(UserDetails.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password123");

        when(customUserDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        ApiResponse<LoginResponse> response = authService.login(loginRequest);

        assertEquals("101", response.code());
    }
}
