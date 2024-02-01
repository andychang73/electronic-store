package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.enumerations.ProductType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.basket.BasketVo;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketBusinessImplTest {

    @Mock
    BasketService basketService;

    @Mock
    BasketBusiness basketBusiness;

    @BeforeEach
    public void init() {
        basketBusiness = new BasketBusinessImpl(basketService);
    }

    @Test
    public void testPutProductIntoBasket_WhenReturnedAnEmptyBasket_ThenReturnedProductWithVoCorrectQuantityAndSubTotal() {

        when(basketService.checkBasketIdOrGenerate(anyString())).thenReturn(UUID.randomUUID().toString());

        when(basketService.getBasketOrGenerate(anyString())).thenReturn(new HashMap<>());


        String basketId = getBasketId();
        ProductEntity product = getProduct();
        BasketVo result = basketBusiness.putProductIntoBasket(basketId, product);


        Map<Integer, ProductVo> basket = result.getBasket();
        ProductVo productVo = basket.get(product.getId());
        assertEquals(productVo.getQuantity(), 1);
        assertEquals(productVo.getSubTotal(), product.getPrice());
    }

    @Test
    public void testPutProductIntoBasket_WhenReturnedNonEmptyBasket_ThenReturnedProductWithCorrectQuantityAndSubTotal() {

        when(basketService.checkBasketIdOrGenerate(anyString())).thenReturn(UUID.randomUUID().toString());

        ProductVo productVo = getProductVo(3, new BigDecimal("30000"));
        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(productVo.getProductId(), productVo);

        when(basketService.getBasketOrGenerate(anyString())).thenReturn(basket);


        String basketId = getBasketId();
        ProductEntity product = getProduct();
        BasketVo result = basketBusiness.putProductIntoBasket(basketId, product);


        Map<Integer, ProductVo> basketVo = result.getBasket();
        ProductVo productResult = basketVo.get(product.getId());
        assertEquals(productResult.getQuantity(), 4);
        assertEquals(productResult.getSubTotal(), new BigDecimal("40000"));
    }

    @Test
    public void testRemoveProductFromBasket_WhenBasketIdDoesNotExist_ThenThrowBusinessException() {

        doThrow(BusinessException.class)
                .when(basketService)
                .getBasketOrThrow(anyString());


        String basketId = getBasketId();
        Integer productId = getProductId();
        assertThrows(BusinessException.class,
                () -> basketBusiness.removeProductFromBasket(basketId, productId),
                "Invalid basket id '1'");


        verify(basketService, never()).getProductFromBasketOrThrow(any(Map.class), anyInt());
        verify(basketService, never()).putBasketBack(anyString(), any(Map.class));
    }

    @Test
    public void testRemoveProductFromBasket_WhenProductNotInBasket_ThenThrowBusinessException() {

        Map<Integer, ProductVo> basket = new HashMap<>();
        when(basketService.getBasketOrThrow(anyString())).thenReturn(basket);

        doThrow(BusinessException.class)
                .when(basketService)
                .getProductFromBasketOrThrow(any(Map.class), anyInt());


        String basketId = getBasketId();
        Integer productId = getProductId();
        assertThrows(BusinessException.class,
                () -> basketBusiness.removeProductFromBasket(basketId, productId),
                "This basket does not have this product! id: '1'");


        verify(basketService, never()).putBasketBack(anyString(), any(Map.class));
    }

    @Test
    public void testRemoveProductFromBasket_WhenQuantityIsEqualOrLessThanOne_ThenRemoveProductFromBasket() {

        when(basketService.getBasketOrThrow(anyString())).thenReturn(new HashMap<>());

        ProductVo productVo = getProductVo(1, new BigDecimal("10000"));
        when(basketService.getProductFromBasketOrThrow(any(Map.class), anyInt())).thenReturn(productVo);


        String basketId = getBasketId();
        Integer productId = getProductId();
        BasketVo result = basketBusiness.removeProductFromBasket(basketId, productId);


        Map<Integer, ProductVo> basketResult = result.getBasket();
        assertNull(basketResult.get(productId));
        verify(basketService, never()).putBasketBack(anyString(), any(Map.class));
    }

    @Test
    public void testRemoveProductFromBasket_WhenQuantityIsGreaterThanOne_ThenProductRemainInBasket(){

        when(basketService.getBasketOrThrow(anyString())).thenReturn(new HashMap<>());

        ProductVo productVo = getProductVo(2, new BigDecimal("20000"));
        when(basketService.getProductFromBasketOrThrow(any(Map.class), anyInt())).thenReturn(productVo);


        String basketId = getBasketId();
        Integer productId = getProductId();
        BasketVo result = basketBusiness.removeProductFromBasket(basketId, productId);


        Map<Integer, ProductVo> basketResult = result.getBasket();
        ProductVo productVoResult = basketResult.get(productId);
        assertNotNull(basketResult.get(productId));
        assertEquals(productVoResult.getQuantity(), 1);
        assertEquals(productVoResult.getSubTotal(), new BigDecimal("10000"));
        verify(basketService, times(1)).putBasketBack(anyString(), any(Map.class));
    }

    private ProductEntity getProduct() {
        return ProductEntity.builder()
                .id(1)
                .type(ProductType.COMPUTER)
                .name("Mac")
                .price(new BigDecimal("10000"))
                .build();
    }

    private ProductVo getProductVo(Integer quantity, BigDecimal subTotal){
        return ProductVo.builder()
                .productId(1)
                .quantity(quantity)
                .unitPrice(new BigDecimal("10000"))
                .subTotal(subTotal)
                .build();
    }

    private String getBasketId() {
        return "1";
    }

    private Integer getProductId() {
        return 1;
    }
}