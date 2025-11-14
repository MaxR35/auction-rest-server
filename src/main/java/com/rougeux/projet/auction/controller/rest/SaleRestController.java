package com.rougeux.projet.auction.controller.rest;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.dto.SaleDto;
import com.rougeux.projet.auction.mapper.HttpMapper;
import com.rougeux.projet.auction.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleRestController {

    private final SaleService saleService;

    public SaleRestController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/api/sales")
    public ResponseEntity<ApiResponse<List<SaleDto>>> getAllSales() {
        return ResponseEntity.ok(saleService.getAll());
    }

    @GetMapping("/api/sales/{itemSlug}")
    public ResponseEntity<ApiResponse<SaleDto>> getSales(@PathVariable String itemSlug) {
        ApiResponse<SaleDto> response = saleService.getById(itemSlug);

        return ResponseEntity.status(HttpMapper.toHttpStatus(response.code())).body(response);
    }
}
