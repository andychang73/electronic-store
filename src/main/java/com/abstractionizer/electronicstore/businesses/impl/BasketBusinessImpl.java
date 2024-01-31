package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.model.product.ProductInBasketDto;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BasketBusinessImpl implements BasketBusiness {

    private final BasketService basketService;

    public BasketBusinessImpl(BasketService basketService) {
        this.basketService = basketService;
    }

    @Override
    public String putProductIntoBasket(@Nullable String basketId, @NonNull final ProductEntity product) {

        basketId = basketService.checkBasketIdOrGenerate(basketId);

        Map<Integer, ProductInBasketDto> basket = basketService.getBasketOrGenerate(basketId);
        log.info("before: {}", basket);

        ProductInBasketDto productInBasketDto = basket.getOrDefault(product.getId(),
                ProductInBasketDto.builder()
                        .productId(product.getId())
                        .productType(product.getType())
                        .productName(product.getName())
                        .quantity(0)
                        .unitPrice(product.getPrice())
                        .build()
        );

        productInBasketDto.setQuantity(productInBasketDto.getQuantity() + 1);
        basket.put(product.getId(), productInBasketDto);

        basketService.putBasketBack(basketId, basket);
        log.info("after: {}", basket);
        return basketId;
    }
}
