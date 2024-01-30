package com.abstractionizer.electronicstore.controllers;

import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.response.SuccessResp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductBusiness productBusiness;

    public AdminController(ProductBusiness productBusiness) {
        this.productBusiness = productBusiness;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product")
    public SuccessResp<Void> createProducts(@RequestBody @NotEmpty List<@Valid CreateProductDto> dtos) {
        productBusiness.createProducts(dtos);
        return new SuccessResp<>();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}")
    public SuccessResp<Void> removeProduct(@PathVariable Integer productId) {
        productBusiness.remove(productId);
        return new SuccessResp<>();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/{type}")
    public SuccessResp<Void> createDeal(@PathVariable @NotNull DealType type, @NotNull final HttpServletRequest request) {
        productBusiness.createDeal(type, request);
        return new SuccessResp<>();
    }
}
