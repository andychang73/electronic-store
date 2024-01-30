package com.abstractionizer.electronicstore.controllers;

import com.abstractionizer.electronicstore.businesses.ProductBusiness;
import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.response.SuccessResp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
