package com.abstractionizer.electronicstore.storage.basket;

import com.abstractionizer.electronicstore.model.product.ProductVo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class Basket {

    private final ConcurrentHashMap<String, Map<Integer, ProductVo>> basket;

    public Basket(){
        basket = new ConcurrentHashMap<>();
    }

}
