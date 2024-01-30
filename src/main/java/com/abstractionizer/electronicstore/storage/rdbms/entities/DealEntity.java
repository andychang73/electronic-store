package com.abstractionizer.electronicstore.storage.rdbms.entities;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("deal")
public class DealEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private DealType type;

    private String policy;

    private Integer applyOrder;

    private Boolean stackable;
}
