package com.abstractionizer.electronicstore.model.product;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private String basketId;
    private Map<Integer,ProductInBasketDto> basket;
}
