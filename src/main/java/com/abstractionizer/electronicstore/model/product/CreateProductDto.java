package com.abstractionizer.electronicstore.model.product;

import com.abstractionizer.electronicstore.enumerations.ProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDto {

    @NotBlank
    private String name;

    @NotNull
    private ProductType type;

    @Min(1)
    private BigDecimal price;
}
