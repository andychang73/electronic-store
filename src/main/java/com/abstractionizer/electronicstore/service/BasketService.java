package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.model.product.ProductInBasketDto;

import java.util.Map;

public interface BasketService {

    String checkBasketIdOrGenerate(String basketId);

    Map<Integer, ProductInBasketDto> getBasketOrGenerate(String basketId);

    void putBasketBack(String basketId, Map<Integer, ProductInBasketDto> basket);

    Map<Integer, ProductInBasketDto> getBasketOrThrow(String basketId);

    ProductInBasketDto getProductFromBasketOrThrow(Map<Integer, ProductInBasketDto> basket, Integer productId);

}
