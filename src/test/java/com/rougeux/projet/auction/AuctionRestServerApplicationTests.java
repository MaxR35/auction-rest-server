package com.rougeux.projet.auction;

import com.rougeux.projet.auction.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AuctionRestServerApplicationTests {

    @Autowired
    private AuthService authService;

    @Test
    void contextLoads() {
        assertThat(authService).isNotNull();
    }
}
