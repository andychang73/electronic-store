package com.abstractionizer.electronicstore.model.product;

import com.abstractionizer.electronicstore.enumerations.ProductType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInBasketDto {
    private Integer productId;
    private ProductType productType;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
}
