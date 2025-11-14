package com.rougeux.projet.auction.controller.rest;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.dto.request.BidRequest;
import com.rougeux.projet.auction.dto.response.BidResponse;
import com.rougeux.projet.auction.mapper.HttpMapper;
import com.rougeux.projet.auction.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidRestController {

    private final BidService bidService;

    public BidRestController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("api/bid/place")
    public ResponseEntity<ApiResponse<BidResponse>> placeBid(@RequestBody BidRequest bidRequest) {
        ApiResponse<BidResponse> response = bidService.placeBid(bidRequest);

        return ResponseEntity.status(HttpMapper.toHttpStatus(response.code())).body(response);
    }
}
