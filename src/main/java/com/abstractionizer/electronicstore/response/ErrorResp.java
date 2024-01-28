package com.abstractionizer.electronicstore.response;

import com.abstractionizer.electronicstore.errors.BaseError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorResp {
    private String code;
    private String message;
    private Object details;

    public ErrorResp(BaseError error, Object details){
        this(error.getCode(), error.getMessage(), details);
    }

    public ErrorResp(String code, String message, Object details){
        this.code = (code == null) ? "" : code;
        this.message = (message == null) ? "" : message;
        this.details = (details == null) ? "" : details;
    }
}
