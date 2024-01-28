package com.abstractionizer.electronicstore.service;

import com.abstractionizer.electronicstore.storage.rdbms.entities.AdminEntity;

public interface AdminService {

    AdminEntity getAdminByNameOrThrow(String name);
}
