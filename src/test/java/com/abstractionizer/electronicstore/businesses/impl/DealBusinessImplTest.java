package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.factories.DealFactory;
import com.abstractionizer.electronicstore.model.deal.DealVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.DealService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealBusinessImplTest {

    @Mock
    DealFactory dealFactory;

    @Mock
    DealService dealService;

    @Mock
    DealBusiness dealBusiness;

    @BeforeEach
    public void init() {
        dealBusiness = new DealBusinessImpl(dealFactory, dealService);
    }

    @Test
    public void testCreateDeal_Successfully() {

        DealType type = DealType.BUY_ONE_GET_ONE_DISCOUNT;
        HttpServletRequest request = mock(HttpServletRequest.class);
        dealBusiness.createDeal(type, request);
    }

    @Test
    public void testApplyDeals_WhenDealsIsEmpty_ThenNeverExecuteApplyDeals() {

        when(dealService.getAllDealsOrderByApplyOrder()).thenReturn(new ArrayList<>());

        dealBusiness.applyDeals(new ReceiptDto());

        verify(dealFactory, never()).applyDeals(any(DealType.class), anyString(), anyString(), any(ReceiptDto.class));
    }

    @Test
    public void testApplyDeals_WhenThereAreTwoDeals_ThenExecuteApplyDealsTwice() {

        DealVo deal1 = new DealVo();
        deal1.setName("deal1");
        deal1.setPolicy("policy");
        deal1.setType(DealType.BUY_ONE_GET_ONE_DISCOUNT);

        DealVo deal2 = new DealVo();
        deal2.setName("deal2");
        deal2.setPolicy("policy");
        deal2.setType(DealType.BUY_ONE_GET_ONE_DISCOUNT);

        when(dealService.getAllDealsOrderByApplyOrder()).thenReturn(Arrays.asList(deal1, deal2));
        when(dealFactory.applyDeals(any(DealType.class), anyString(), anyString(), any(ReceiptDto.class)))
                .thenReturn(new ReceiptDto())
                .thenReturn(new ReceiptDto());


        dealBusiness.applyDeals(new ReceiptDto());


        verify(dealFactory, times(2)).applyDeals(any(DealType.class), anyString(), anyString(), any(ReceiptDto.class));
    }
}