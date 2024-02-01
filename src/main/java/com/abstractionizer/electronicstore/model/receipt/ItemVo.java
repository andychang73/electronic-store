package com.abstractionizer.electronicstore.model.receipt;


import com.abstractionizer.electronicstore.enumerations.ProductType;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    private Integer productId;
    private ProductType productType;
    private String productName;
    private Integer quantity;
}
