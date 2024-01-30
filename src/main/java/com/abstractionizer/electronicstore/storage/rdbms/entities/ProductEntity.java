package com.abstractionizer.electronicstore.storage.rdbms.entities;

import com.abstractionizer.electronicstore.enumerations.ProductStatus;
import com.abstractionizer.electronicstore.enumerations.ProductType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@TableName("product")
public class ProductEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private ProductType type;

    private BigDecimal price;

    private ProductStatus status;
}
