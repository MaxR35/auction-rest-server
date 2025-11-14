package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.dal.ISaleDao;
import com.rougeux.projet.auction.dto.SaleDto;
import com.rougeux.projet.auction.mapper.entity.SaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rougeux.projet.auction.service.utils.CodeService.CD_DATA_SUCCESS;
import static com.rougeux.projet.auction.service.utils.CodeService.CD_ERR_NOTFOUND;

@Service
public class SaleService {

    private final ISaleDao saleDao;

    public SaleService(ISaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Lazy
    private BidService bidService;

    @Autowired
    public void setBidService(@Lazy BidService bidService) {
        this.bidService = bidService;
    }

    public ApiResponse<List<SaleDto>> getAll() {
        return ApiResponseFactory.success(CD_DATA_SUCCESS, "sales.findAll.success",
                saleDao.findAll().stream().map(SaleMapper::toDto).toList());
    }

    public ApiResponse<SaleDto> getById(String itemSlug) {
        Sale sale = saleDao.findById(itemSlug);

        if(sale == null) {
            return ApiResponseFactory.error(CD_ERR_NOTFOUND, "sales.findBy.error.notFound");
        }
        sale.setBids(bidService.findAllBySlug(sale.getSlug()));

        return ApiResponseFactory.success(CD_DATA_SUCCESS, "sales.findBy.success",
                SaleMapper.toDto(sale));
    }

    public Sale getEntityBySlug(String slug) {
        return saleDao.findById(slug);
    }

    public void save(Sale sale) {
        saleDao.save(sale);
    }
}
