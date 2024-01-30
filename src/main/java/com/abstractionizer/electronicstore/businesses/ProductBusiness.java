package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.product.CreateProductDto;

import java.util.List;

public interface ProductBusiness {

    void createProducts(List<CreateProductDto> dtos);
}
