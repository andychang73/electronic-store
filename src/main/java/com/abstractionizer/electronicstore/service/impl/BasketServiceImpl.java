package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.model.product.ProductInBasketDto;
import com.abstractionizer.electronicstore.service.BasketService;
import com.abstractionizer.electronicstore.storage.basket.Basket;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    public Map<Integer, ProductInBasketDto> getBasketOrGenerate(@NotNull final String basketId) {
        return Optional.ofNullable(basket.getBasket().get(basketId)).orElse(new HashMap<>());
    }

    @Override
    public void putBasketBack(@NonNull final String basketId, @NonNull final Map<Integer, ProductInBasketDto> basket) {
        this.basket.getBasket().put(basketId, basket);
    }

    @SneakyThrows
    public static void main(String[] args) {
        Map<Integer,ProductInBasketDto> basket = new HashMap<>();
        ProductInBasketDto dto1 = new ProductInBasketDto();
        dto1.setProductId(1);
        dto1.setProductName("name");
        dto1.setUnitPrice(BigDecimal.TEN);
        dto1.setQuantity(1);

        basket.put(dto1.getProductId(), dto1);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(basket));


        String b = "{\"1\":{\"productId\":1,\"productName\":\"name\",\"unitPrice\":10,\"quantity\":1}}";
        Map<Integer, ProductInBasketDto> map = objectMapper.readValue(b, Map.class);
        System.out.println(map);
    }
}
