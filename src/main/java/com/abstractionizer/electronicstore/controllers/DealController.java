package com.abstractionizer.electronicstore.controllers;


import com.abstractionizer.electronicstore.businesses.DealBusiness;
import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.response.SuccessResp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deal")
public class DealController {

    private final DealBusiness dealBusiness;

    public DealController(DealBusiness dealBusiness) {
        this.dealBusiness = dealBusiness;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{type}")
    public SuccessResp<Void> createDeal(@PathVariable @NotNull DealType type, @NotNull final HttpServletRequest request) {
        dealBusiness.createDeal(type, request);
        return new SuccessResp<>();
    }
}
