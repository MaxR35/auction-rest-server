package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.dto.request.LoginRequest;
import com.rougeux.projet.auction.dto.response.LoginResponse;
import com.rougeux.projet.auction.service.security.CustomUserDetailsService;
import com.rougeux.projet.auction.service.security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.rougeux.projet.auction.service.utils.CodeService.CD_ERR_USER_AUTHENTICATED;
import static com.rougeux.projet.auction.service.utils.CodeService.CD_OK_USER_AUTHENTICATED;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (userDetails == null) {
            return ApiResponseFactory.error(CD_ERR_USER_AUTHENTICATED, "login.error.invalidCredentials");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return ApiResponseFactory.error(CD_ERR_USER_AUTHENTICATED, "login.error.invalidCredentials");
        }

        String jwt = jwtService.generateToken(userDetails);

        return ApiResponseFactory.success(CD_OK_USER_AUTHENTICATED, "login.success",
                new LoginResponse(userDetails, jwt));
    }
}
