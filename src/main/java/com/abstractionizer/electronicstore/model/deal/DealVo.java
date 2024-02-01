package com.abstractionizer.electronicstore.model.deal;

import com.abstractionizer.electronicstore.enumerations.DealType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DealVo {
    private String name;
    private String policy;
    private DealType type;
}
