package com.abstractionizer.electronicstore.handlers.deals;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.deal.CreateOneGetOneDealDto;
import com.abstractionizer.electronicstore.model.deal.Discount;
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

import static com.abstractionizer.electronicstore.errors.Error.BAD_REQUEST_ERROR;

@Slf4j
@Component
public class BuyOneGetOneDiscountHandlerAbstract extends AbstractDealHandler<CreateOneGetOneDealDto> {

    private final ProductService productService;
    public BuyOneGetOneDiscountHandlerAbstract(ObjectMapper objectMapper, DealService dealService,
                                               ProductService productService) {
        super(objectMapper, dealService);
        this.productService = productService;
    }

    @Transactional
    @SneakyThrows
    @Override
    public void createDeal(@NonNull final HttpServletRequest request) {
        CreateOneGetOneDealDto dto = this.convertRequestToDeal(request);
        this.validateCreateDealDto(dto);

        productService.ifProductNotExistsThenThrow(dto.getProductId());
        dealService.ifDealNameExistThenThrow(dto.getName());
        dealService.ifApplyOrderExistThenThrow(dto.getApplyOrder());

        Discount discount = Discount.builder()
                .discount(dto.getDiscount())
                .build();

        DealEntity deal = DealEntity.builder()
                .name(dto.getName())
                .type(DealType.BUY_ONE_GET_ONE_DISCOUNT)
                .policy(objectMapper.writeValueAsString(discount))
                .applyOrder(dto.getApplyOrder())
                .stackable(dto.getStackable())
                .build();

        dealService.insert(deal);
    }

    @SneakyThrows
    @Override
    public CreateOneGetOneDealDto convertRequestToDeal(@NonNull final HttpServletRequest request) {
        return objectMapper.readValue(request.getInputStream(), CreateOneGetOneDealDto.class);
    }

    @Override
    public void validateCreateDealDto(@NonNull final CreateOneGetOneDealDto dto) {
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

    @Override
    public DealType getDealType() {
        return DealType.BUY_ONE_GET_ONE_DISCOUNT;
    }
}
