package com.abstractionizer.electronicstore.handlers.deals;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.deal.CreateOneGetOneDiscountPolicy;
import com.abstractionizer.electronicstore.model.deal.BuyOneGetOneDiscountPolicy;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.DealService;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static com.abstractionizer.electronicstore.errors.Error.BAD_REQUEST_ERROR;

@Slf4j
@Component
public class BuyOneGetOneDiscountDealHandler extends AbstractDealHandler<CreateOneGetOneDiscountPolicy> {

    private final ProductService productService;
    public BuyOneGetOneDiscountDealHandler(ObjectMapper objectMapper, DealService dealService,
                                           ProductService productService) {
        super(objectMapper, dealService);
        this.productService = productService;
    }

    @Transactional
    @SneakyThrows
    @Override
    public void createDeal(@NonNull final HttpServletRequest request) {
        CreateOneGetOneDiscountPolicy dto = this.convertRequestToDeal(request);
        this.validateCreateDealDto(dto);

        productService.ifProductNotExistsThenThrow(dto.getProductId());
        dealService.ifDealNameExistThenThrow(dto.getName());
        dealService.ifApplyOrderExistThenThrow(dto.getApplyOrder());

        BuyOneGetOneDiscountPolicy buyOneGetOneDiscountPolicy = BuyOneGetOneDiscountPolicy.builder()
                .productId(dto.getProductId())
                .discount(dto.getDiscount())
                .build();

        DealEntity deal = DealEntity.builder()
                .name(dto.getName())
                .type(DealType.BUY_ONE_GET_ONE_DISCOUNT)
                .policy(objectMapper.writeValueAsString(buyOneGetOneDiscountPolicy))
                .applyOrder(dto.getApplyOrder())
                .stackable(dto.getStackable())
                .build();

        dealService.insert(deal);
    }

    @Override
    public DealType getDealType() {
        return DealType.BUY_ONE_GET_ONE_DISCOUNT;
    }
    @Override
    public ReceiptDto apply(@NonNull final ReceiptDto dto, @NonNull final String dealName, @NonNull final String policyStr) {

        CreateOneGetOneDiscountPolicy dealParam = this.policyStrToDealParam(policyStr);

        ProductVo productVo = dto.getBasket().get(dealParam.getProductId());
        if(Objects.isNull(productVo)){
            return dto;
        }

        dto.getDealsApplied().add(dealName);

        Integer quantity = productVo.getQuantity();
        BigDecimal subTotal = BigDecimal.ZERO;
        boolean isSecondItem = false;

        for(int i = 0; i < quantity; i++){
            BigDecimal unitPrice = productVo.getUnitPrice();
            if(isSecondItem){
                unitPrice = unitPrice.multiply(dealParam.getDiscount());
            }
            subTotal = subTotal.add(unitPrice);
            isSecondItem = !isSecondItem;
        }

        productVo.setSubTotal(subTotal);

        return dto;
    }

    @SneakyThrows
    @Override
    public CreateOneGetOneDiscountPolicy convertRequestToDeal(@NonNull final HttpServletRequest request) {
        return objectMapper.readValue(request.getInputStream(), CreateOneGetOneDiscountPolicy.class);
    }

    @SneakyThrows
    @Override
    protected CreateOneGetOneDiscountPolicy policyStrToDealParam(@NonNull final String policy) {
        return objectMapper.readValue(policy, CreateOneGetOneDiscountPolicy.class);
    }


    @Override
    public void validateCreateDealDto(@NonNull final CreateOneGetOneDiscountPolicy dto) {
        this.validateName(dto.getName());
        this.validateApplyOrder(dto.getApplyOrder());
        this.validateStackable(dto.getStackable());

        if(dto.getProductId() == null || dto.getProductId() < 1){
            throw new BusinessException(BAD_REQUEST_ERROR, "CreateOneGetOneDiscountDto productId attribute must not be null or value must be greater than 1");
        }
        if(dto.getDiscount() == null || dto.getDiscount().compareTo(BigDecimal.ZERO) <= 0 || dto.getDiscount().compareTo(BigDecimal.ONE) >= 0){
            throw new BusinessException(BAD_REQUEST_ERROR, "CreateOneGetOneDiscountDto discount attribute must not be null and should be greater than 0 and less and 1");
        }

    }
}
