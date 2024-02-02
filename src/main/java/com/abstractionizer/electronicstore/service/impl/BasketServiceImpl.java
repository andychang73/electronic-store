package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.storage.basket.Basket;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.abstractionizer.electronicstore.errors.Error.DATA_NOT_FOUND;

@Service
public class BasketServiceImpl implements BasketService {

    private final Basket basket;

    public BasketServiceImpl(Basket basket) {
        this.basket = basket;
    }

    @Override
    public String checkBasketIdOrGenerate(@Nullable final String basketId) {
        return Optional.ofNullable(basketId).orElse(UUID.randomUUID().toString());
    }

    @Override
    public Map<Integer, ProductVo> getBasketOrGenerate(@NotNull final String basketId) {
        return Optional.ofNullable(basket.getBasket().get(basketId)).orElse(new HashMap<>());
    }

    @Override
    public void putBasketBack(@NonNull final String basketId, @NonNull final Map<Integer, ProductVo> basket) {
        this.basket.getBasket().put(basketId, basket);
    }

    @Override
    public Map<Integer, ProductVo> getBasketOrThrow(@NonNull final String basketId) {
        return Optional.ofNullable(basket.getBasket().get(basketId))
                .orElseThrow(() -> new BusinessException(DATA_NOT_FOUND, String.format("Invalid basket id '%s'", basketId)));
    }

    @Override
    public ProductVo getProductFromBasketOrThrow(@NonNull final Map<Integer, ProductVo> basket, @NonNull final Integer productId) {
        return Optional.ofNullable(basket.get(productId))
                .orElseThrow(() -> new BusinessException(DATA_NOT_FOUND, String.format("This basket does not have this product! id: '%s'", productId)));
    }
}
