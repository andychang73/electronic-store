package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.model.product.ProductVo;

import java.util.Map;

public interface BasketService {

    String checkBasketIdOrGenerate(String basketId);

    Map<Integer, ProductVo> getBasketOrGenerate(String basketId);

    void putBasketBack(String basketId, Map<Integer, ProductVo> basket);

    Map<Integer, ProductVo> getBasketOrThrow(String basketId);

    ProductVo getProductFromBasketOrThrow(Map<Integer, ProductVo> basket, Integer productId);

}
