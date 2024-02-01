package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.businesses.ReceiptBusiness;
import com.abstractionizer.electronicstore.enumerations.ProductType;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptBusinessImplTest {

    @Mock
    DealBusiness dealBusiness;

    @Mock
    BasketService basketService;

    @Mock
    ReceiptService receiptService;

    @Mock
    ReceiptBusiness receiptBusiness;

    @BeforeEach
    public void init() {
        receiptBusiness = new ReceiptBusinessImpl(dealBusiness, basketService, receiptService);
    }

    @Test
    public void testGetReceipt_NoValidationNeeded_AllMethodsAreExecuted() {

        when(basketService.getBasketOrThrow(anyString())).thenReturn(new HashMap<>());

        ProductVo mac = new ProductVo();
        mac.setProductId(1);
        mac.setProductName("mac");
        mac.setProductType(ProductType.COMPUTER);
        mac.setQuantity(2);
        mac.setUnitPrice(new BigDecimal("10000"));
        mac.setSubTotal(new BigDecimal("20000"));

        ProductVo iPhone = new ProductVo();
        iPhone.setProductId(2);
        iPhone.setProductName("iPhone");
        iPhone.setProductType(ProductType.MOBILE_PHONE);
        iPhone.setQuantity(2);
        iPhone.setUnitPrice(new BigDecimal("5000"));
        iPhone.setSubTotal(new BigDecimal("10000"));

        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(1, mac);
        basket.put(2, iPhone);

        ReceiptDto dto = new ReceiptDto();
        dto.setBasket(basket);
        dto.setDealsApplied(new ArrayList<>());

        when(dealBusiness.applyDeals(any(ReceiptDto.class))).thenReturn(dto);

        receiptBusiness.getReceipt("1");

        verify(basketService, times(1)).getBasketOrThrow(anyString());
        verify(dealBusiness, times(1)).applyDeals(any(ReceiptDto.class));
    }

}