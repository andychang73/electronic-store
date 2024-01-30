package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductBusiness {

    void createProducts(List<CreateProductDto> dtos);

    void remove(Integer productId);

    void createDeal(DealType type, HttpServletRequest request);
}
