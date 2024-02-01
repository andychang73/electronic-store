package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.factories.DealFactory;
import com.abstractionizer.electronicstore.model.deal.DealVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.DealService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealBusinessImpl implements DealBusiness {

    private final DealFactory dealFactory;
    private final DealService dealService;

    public DealBusinessImpl(DealFactory dealFactory, DealService dealService) {
        this.dealFactory = dealFactory;
        this.dealService = dealService;
    }

    @Override
    public void createDeal(@NonNull final DealType type, @NonNull final HttpServletRequest request) {
        dealFactory.createDeal(type, request);
    }

    @Override
    public ReceiptDto applyDeals(@NonNull ReceiptDto receiptDto) {
        List<DealVo> deals = dealService.getAllDealsOrderByApplyOrder();

        for(DealVo deal : deals){
            receiptDto = dealFactory.applyDeals(deal.getType(), deal.getName(), deal.getPolicy(), receiptDto);
        }

        return receiptDto;
    }
}
