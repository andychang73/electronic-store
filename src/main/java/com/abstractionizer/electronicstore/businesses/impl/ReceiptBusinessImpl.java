package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.businesses.ReceiptBusiness;
import com.abstractionizer.electronicstore.converters.ProductConverter;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.model.receipt.ReceiptVo;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.service.ReceiptService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ReceiptBusinessImpl implements ReceiptBusiness {

    private final DealBusiness dealBusiness;
    private final BasketService basketService;
    private final ReceiptService receiptService;

    public ReceiptBusinessImpl(DealBusiness dealBusiness, BasketService basketService,
                               ReceiptService receiptService) {
        this.dealBusiness = dealBusiness;
        this.basketService = basketService;
        this.receiptService = receiptService;
    }


    @Override
    public ReceiptVo getReceipt(@NonNull final String basketId) {

        Map<Integer, ProductVo> basket = basketService.getBasketOrThrow(basketId);

        ReceiptDto receiptDto = ReceiptDto.builder()
                .basket(basket)
                .dealsApplied(new ArrayList<>())
                .build();

        receiptDto = dealBusiness.applyDeals(receiptDto);

        return ReceiptVo.builder()
                .items(ProductConverter.INSTANCE.toItems(receiptDto.getBasket().values()))
                .dealsApplied(receiptDto.getDealsApplied())
                .totalPrice(receiptService.getTotalPrice(receiptDto.getBasket().values()))
                .build();
    }
}
