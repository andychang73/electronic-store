package com.abstractionizer.electronicstore.storage.rdbms.entities;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("deal")
public class DealEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private DealType type;

    private String policy;
}
