package com.abstractionizer.electronicstore.converters;

import com.abstractionizer.electronicstore.model.product.CreateProductDto;
import com.abstractionizer.electronicstore.storage.rdbms.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ProductConverter {

    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "price", source = "price")
    })
    ProductEntity toProductEntity(CreateProductDto dto);

    List<ProductEntity> toProductEntities(Collection<CreateProductDto> dtos);
}
