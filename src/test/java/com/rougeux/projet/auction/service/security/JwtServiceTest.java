package com.rougeux.projet.auction.service.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("mock")
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateToken_ShouldReturnJwtValue() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("admin");

        Jwt mockedJwt = mock(Jwt.class);
        when(mockedJwt.getTokenValue()).thenReturn("mockedJwtToken");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockedJwt);
        String token = jwtService.generateToken(userDetails);

        assertEquals("mockedJwtToken", token);
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }
}
