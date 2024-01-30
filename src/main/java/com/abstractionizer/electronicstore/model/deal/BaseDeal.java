package com.abstractionizer.electronicstore.model.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseDeal {
    protected String name;
    protected Integer applyOrder;
    protected Boolean stackable;
}
