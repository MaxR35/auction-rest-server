package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.dal.ISaleDao;
import com.rougeux.projet.auction.dto.SaleDto;
import com.rougeux.projet.auction.service.utils.LocaleHelper;
import org.junit.jupiter.api.BeforeEach;
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
class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private ISaleDao saleDao;

    @Mock
    private BidService bidService;

    @InjectMocks
    ApiResponseFactory apiResponseFactory;

    @Mock
    private LocaleHelper localeHelper;

    @BeforeEach
    void setUp() {
        saleService.setBidService(bidService);
    }

    @Test
    void getAllSales() {
        Sale sale = new Sale();

        when(saleDao.findAll()).thenReturn(List.of(sale));
        when(localeHelper.i18N("sales.findAll.success")).thenReturn("Success message");
        ApiResponse<List<SaleDto>> response = saleService.getAll();

        assertEquals("202", response.code());
    }

    @Test
    void findBySlug_Error() {
        when(saleDao.findById("sale-slug-5415d4z65d1z")).thenReturn(null);
        when(localeHelper.i18N("sales.findBy.error.notFound")).thenReturn("Not found message");

        ApiResponse<SaleDto> response = saleService.getById("sale-slug-5415d4z65d1z");

        assertEquals("404", response.code());
    }

    @Test
    void findBySlug_Success() {
        Sale sale = new Sale();
        sale.setSlug("sale-slug-5415d4z65d1z");
        Bid bid = new Bid();

        when(saleDao.findById("sale-slug-5415d4z65d1z")).thenReturn(sale);
        when(bidService.findAllBySlug(sale.getSlug())).thenReturn(List.of(bid));
        when(localeHelper.i18N("sales.findBy.success")).thenReturn("Success message");

        ApiResponse<SaleDto> response = saleService.getById("sale-slug-5415d4z65d1z");

        assertEquals("202", response.code());
    }

    @Test
    void getEntityByUsername_Success() {
        Sale sale = new Sale();

        when(saleDao.findById("slug-da5s4a5sf4a5s")).thenReturn(sale);
        assertNotNull(saleService.getEntityBySlug("slug-da5s4a5sf4a5s"));
    }

    @Test
    void save() {
        Sale sale = new Sale();
        saleService.save(sale);

        verify(saleDao).save(any(Sale.class));
    }
}

