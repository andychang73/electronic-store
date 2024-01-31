package com.abstractionizer.electronicstore.controllers;

import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.model.product.BasketDto;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.response.SuccessResp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductBusiness productBusiness;

    public ProductController(ProductBusiness productBusiness) {
        this.productBusiness = productBusiness;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public SuccessResp<Void> createProducts(@RequestBody @NotEmpty List<@Valid CreateProductDto> dtos) {
        productBusiness.createProducts(dtos);
        return new SuccessResp<>();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public SuccessResp<Void> removeProduct(@PathVariable Integer productId) {
        productBusiness.remove(productId);
        return new SuccessResp<>();
    }

    @PutMapping({"/{productId}/select"})
    public SuccessResp<BasketDto> putProductIntoBasket(@PathVariable Integer productId,
                                                       @RequestParam(required = false) String basketId){
        return new SuccessResp<>(productBusiness.selectProduct(productId, basketId));
    }

    @PutMapping("/{productId}/remove")
    public SuccessResp<BasketDto> removeAProductFromBasket(@PathVariable Integer productId,
                                                           @RequestParam String basketId){
        return new SuccessResp<>(productBusiness.removeProduct(productId, basketId));
    }
}
