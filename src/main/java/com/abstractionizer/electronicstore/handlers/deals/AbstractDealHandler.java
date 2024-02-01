package com.abstractionizer.electronicstore.handlers.deals;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.deal.BaseDeal;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.DealService;
import com.abstractionizer.electronicstore.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import static com.abstractionizer.electronicstore.errors.Error.BAD_REQUEST_ERROR;

public abstract class AbstractDealHandler<T extends BaseDeal> {

    protected final DealService dealService;
    protected final ProductService productService;
    protected final ObjectMapper objectMapper;


    protected AbstractDealHandler(ObjectMapper objectMapper, DealService dealService, ProductService productService) {
        this.objectMapper = objectMapper;
        this.dealService = dealService;
        this.productService = productService;
    }

    public abstract void createDeal(HttpServletRequest request);
    public abstract DealType getDealType();

    public abstract ReceiptDto apply(ReceiptDto dto, String dealName, String policyStr);
    protected abstract T convertRequestToDeal(HttpServletRequest request);

    protected abstract T policyStrToDealParam(String policy);
    protected abstract void validateCreateDealDto(T createDealDto);

    protected void validateName(String name){
        if(StringUtils.isEmpty(name)){
            throw new BusinessException(BAD_REQUEST_ERROR, "Name attribute must not be null or empty'");
        }
    }
    protected void validateApplyOrder(Integer applyOrder){
        if(applyOrder == null || applyOrder < 1){
            throw new BusinessException(BAD_REQUEST_ERROR, "Apply order attribute must not be null and must be greater than 1");
        }
    }

    protected void validateStackable(Boolean stackable){
        if(stackable == null){
            throw new BusinessException(BAD_REQUEST_ERROR, "Stackable must not be null");
        }
    }
}
