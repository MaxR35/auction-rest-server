package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IBidDao;
import com.rougeux.projet.auction.dto.request.BidRequest;
import com.rougeux.projet.auction.dto.response.BidResponse;
import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @InjectMocks
    BidService bidService;

    @Mock
    IBidDao bidDao;

    @Mock
    UserService userService;

    @Mock
    SaleService saleService;

    @InjectMocks
    ApiResponseFactory apiResponseFactory;

    @Mock
    private LocaleHelper localeHelper;
    private SecurityContext originalContext;

    @BeforeEach
    void setUp() {
        bidService.setSaleService(saleService);
        originalContext = SecurityContextHolder.getContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.setContext(originalContext);
    }

    private void mockSecurityContext(User user) {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(user.getUsername());
        when(auth.getName()).thenReturn(user.getUsername());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }


    @Test
    void placeBid_SaleNotFound() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("non-existent");

        when(saleService.getEntityBySlug("non-existent")).thenReturn(null);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);
        assertEquals("404", response.code());
    }

    @Test
    void placeBid_SaleEnded() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("sale-1");

        Sale sale = new Sale();
        sale.setEndAt(LocalDateTime.now().minusHours(1));

        when(saleService.getEntityBySlug("sale-1")).thenReturn(sale);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);
        assertEquals("720", response.code());
    }

    @Test
    void placeBid_UserNotLoggedIn() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("sale-2");

        Sale sale = new Sale();
        sale.setEndAt(LocalDateTime.now().plusHours(1));

        when(saleService.getEntityBySlug("sale-2")).thenReturn(sale);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);
        assertEquals("401", response.code());
    }

    @Test
    void placeBid_AmountTooLow() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("sale-3");
        bidRequest.setAmount(50);

        Sale sale = new Sale();
        sale.setEndAt(LocalDateTime.now().plusHours(1));
        sale.setStartingPrice(100);

        when(saleService.getEntityBySlug("sale-3")).thenReturn(sale);

        User user = new User();
        user.setId("user1");
        user.setUsername("testUser");
        user.setCredit(200);

        mockSecurityContext(user);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);
        assertEquals("721", response.code());
    }

    @Test
    void placeBid_UserCreditTooLow() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("sale-4");
        bidRequest.setAmount(200);

        Sale sale = new Sale();
        sale.setEndAt(LocalDateTime.now().plusHours(1));
        sale.setStartingPrice(100);

        User user = new User();
        user.setUsername("testUser");
        user.setCredit(50);

        mockSecurityContext(user);
        when(saleService.getEntityBySlug("sale-4")).thenReturn(sale);
        when(userService.getEntityByUsername("testUser")).thenReturn(user);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);
        assertEquals("722", response.code());
    }

    @Test
    void placeBid_Success() {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSlug("sale-5");
        bidRequest.setAmount(150);

        Sale sale = new Sale();
        sale.setSlug("sale-5");
        sale.setEndAt(LocalDateTime.now().plusHours(1));
        sale.setStartingPrice(100);

        when(saleService.getEntityBySlug("sale-5")).thenReturn(sale);
        when(bidDao.findBids("sale-5")).thenReturn(new ArrayList<>());

        User user = new User();
        user.setId("user1");
        user.setUsername("testUser");
        user.setCredit(200);

        mockSecurityContext(user);
        when(userService.getEntityByUsername("testUser")).thenReturn(user);

        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);

        assertEquals("209", response.code());
        assertEquals(50, user.getCredit());
        verify(bidDao).save(any(Bid.class));
        verify(saleService).save(any(Sale.class));
    }
}
