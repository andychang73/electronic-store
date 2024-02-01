package com.abstractionizer.electronicstore.model.receipt;

import com.abstractionizer.electronicstore.model.product.ProductVo;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
    private Map<Integer, ProductVo> basket;
    private List<String> dealsApplied;
    private BigDecimal totalPrice;
}
