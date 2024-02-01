package com.abstractionizer.electronicstore.model.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CreateOneGetOneDiscountPolicy extends BaseDeal {
    private Integer productId;
    private BigDecimal discount;
}
