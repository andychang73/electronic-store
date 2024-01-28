package com.abstractionizer.electronicstore.service.impl;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.service.AdminService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.AdminEntity;
import com.abstractionizer.electronicstore.storage.rdbms.mappers.AdminMapper;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.abstractionizer.electronicstore.errors.Error.INVALID_ACCOUNT_OR_PASSWORD;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminEntity getAdminByNameOrThrow(@NonNull final String name) {
        return Optional.ofNullable(adminMapper.selectByName(name)).
                orElseThrow(() -> new BusinessException(INVALID_ACCOUNT_OR_PASSWORD));
    }
}
