package com.abstractionizer.electronicstore.model.receipt;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptVo {
    private List<ItemVo> items;
    private List<String> dealsApplied;
    private BigDecimal totalPrice;
}
