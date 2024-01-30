package com.abstractionizer.electronicstore.model.deal;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    private BigDecimal discount;
}
