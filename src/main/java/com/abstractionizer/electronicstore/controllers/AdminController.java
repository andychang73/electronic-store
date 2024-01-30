package com.abstractionizer.electronicstore.controllers;

import com.abstractionizer.electronicstore.businesses.AdminLoginBusiness;
import com.abstractionizer.electronicstore.model.login.LoginDto;
import com.abstractionizer.electronicstore.response.SuccessResp;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminLoginBusiness adminLoginBusiness;

    public AdminController(AdminLoginBusiness adminLoginBusiness) {
        this.adminLoginBusiness = adminLoginBusiness;
    }

    @PostMapping("/login")
    public SuccessResp<String> login(@RequestBody @Valid LoginDto dto){
        return new SuccessResp<>(adminLoginBusiness.login(dto));
    }
}
