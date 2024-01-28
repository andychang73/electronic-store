package com.abstractionizer.electronicstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResp<T>{

    private T data;

    public SuccessResp(){
        this.data = (T)"SUCCESS";
    }
    public SuccessResp(T data){
        this.data = data;
    }
}
