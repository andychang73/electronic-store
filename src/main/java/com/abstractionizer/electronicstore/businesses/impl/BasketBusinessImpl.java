package com.abstractionizer.electronicstore.businesses.impl;

import com.abstractionizer.electronicstore.businesses.BasketBusiness;
import com.abstractionizer.electronicstore.model.basket.BasketVo;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service
public class BasketBusinessImpl implements BasketBusiness {

    private final BasketService basketService;

    public BasketBusinessImpl(BasketService basketService) {
        this.basketService = basketService;
    }

    @Override
    public BasketVo putProductIntoBasket(@Nullable String basketId, @NonNull final ProductEntity product) {

        basketId = basketService.checkBasketIdOrGenerate(basketId);

        Map<Integer, ProductVo> basket = basketService.getBasketOrGenerate(basketId);
        log.info("before: {}", basket);

        ProductVo productVo = basket.getOrDefault(product.getId(),
                ProductVo.builder()
                        .productId(product.getId())
                        .productType(product.getType())
                        .productName(product.getName())
                        .quantity(0)
                        .unitPrice(product.getPrice())
                        .subTotal(BigDecimal.ZERO)
                        .build()
        );

        productVo.setQuantity(productVo.getQuantity() + 1);
        productVo.setSubTotal(productVo.getSubTotal().add(productVo.getUnitPrice()));
        basket.put(product.getId(), productVo);

        basketService.putBasketBack(basketId, basket);
        log.info("after: {}", basket);

        return BasketVo.builder()
                .basketId(basketId)
                .basket(basket)
                .build();
    }

    @Override
    public BasketVo removeProductFromBasket(@NonNull final String basketId, @NonNull final Integer productId) {

        Map<Integer, ProductVo> basket = basketService.getBasketOrThrow(basketId);

        ProductVo productVo = basketService.getProductFromBasketOrThrow(basket, productId);

        if(productVo.getQuantity() <= 1){
            basket.remove(productId);
            return BasketVo.builder()
                    .basketId(basketId)
                    .basket(basket)
                    .build();
        }

        productVo.setQuantity(productVo.getQuantity() - 1);
        productVo.setSubTotal(productVo.getSubTotal().subtract(productVo.getUnitPrice()));
        basket.put(productId, productVo);

        basketService.putBasketBack(basketId, basket);

        return BasketVo.builder()
                .basketId(basketId)
                .basket(basket)
                .build();
    }
}
