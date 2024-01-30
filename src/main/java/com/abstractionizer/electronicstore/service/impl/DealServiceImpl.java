package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.service.DealService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;
import com.abstractionizer.electronicstore.storage.rdbms.mappers.DealMapper;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import static com.abstractionizer.electronicstore.errors.Error.BAD_REQUEST_ERROR;

@Service
public class DealServiceImpl implements DealService {

    private final DealMapper dealMapper;

    public DealServiceImpl(DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }

    @Override
    public void insert(@NonNull final DealEntity deal) {
        dealMapper.insert(deal);
    }

    @Override
    public void ifApplyOrderExistThenThrow(@NotNull final Integer applyOrder) {
        if(countByNameOrApplyOrder(null, applyOrder) > 0){
            throw new BusinessException(BAD_REQUEST_ERROR, String.format("Apply order '%s' already exists", applyOrder));
        }
    }

    @Override
    public void ifDealNameExistThenThrow(@NotNull final String name) {
        if(countByNameOrApplyOrder(name, null) > 0){
            throw new BusinessException(BAD_REQUEST_ERROR, String.format("Deal name '%s' already exists", name));
        }
    }

    private int countByNameOrApplyOrder(@Nullable final String name, @Nullable final Integer applyOrder){
        return dealMapper.countByNameOrApplyOrder(name, applyOrder);
    }
}
