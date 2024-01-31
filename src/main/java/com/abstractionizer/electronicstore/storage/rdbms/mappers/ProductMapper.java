package com.abstractionizer.electronicstore.storage.rdbms.mappers;

import com.abstractionizer.electronicstore.enumerations.ProductStatus;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface ProductMapper extends BaseMapper<ProductEntity> {
    int countByNames(Collection<String> names);

    void insertBatch(Collection<ProductEntity> entities);

    ProductEntity findById(Integer id, ProductStatus status);

    int updateProduct(ProductEntity entity);

    ProductEntity selectByIdForUpdate(Integer id, ProductStatus status);

    int adjustStock(Integer id, Integer adjustment);
}
