package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.model.product.BasketDto;
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
    public BasketDto putProductIntoBasket(@Nullable String basketId, @NonNull final ProductEntity product) {

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

        return BasketDto.builder()
                .basketId(basketId)
                .basket(basket)
                .build();
    }

    @Override
    public BasketDto removeProductFromBasket(@NonNull final String basketId, @NonNull final Integer productId) {

        Map<Integer, ProductInBasketDto> basket = basketService.getBasketOrThrow(basketId);

        ProductInBasketDto productInBasket = basketService.getProductFromBasketOrThrow(basket, productId);

        if(productInBasket.getQuantity() <= 1){
            basket.remove(productId);
            return BasketDto.builder()
                    .basketId(basketId)
                    .basket(basket)
                    .build();
        }

        productInBasket.setQuantity(productInBasket.getQuantity() - 1);
        basket.put(productId, productInBasket);

        basketService.putBasketBack(basketId, basket);

        return BasketDto.builder()
                .basketId(basketId)
                .basket(basket)
                .build();
    }
}
