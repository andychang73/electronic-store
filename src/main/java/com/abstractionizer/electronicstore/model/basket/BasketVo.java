package com.abstractionizer.electronicstore.model.basket;

import com.abstractionizer.electronicstore.model.product.ProductVo;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketVo {
    private String basketId;
    private Map<Integer, ProductVo> basket;
}
