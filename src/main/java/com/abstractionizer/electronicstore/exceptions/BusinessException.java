package com.abstractionizer.electronicstore.exceptions;

import com.abstractionizer.electronicstore.errors.BaseError;

public class BusinessException extends RuntimeException{
    private int httpStatus;
    private String code;
    private String message;
    private String details;

    public BusinessException(BaseError error){
        this(error, null);
    }

    public BusinessException(BaseError error, String details){
        this(error.getHttpStatus(), error.getCode(), error.getMessage(), details);
    }

    public BusinessException(int httpStatus, String code, String message, String details){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public int getHttpStatus(){
        return this.httpStatus;
    }

    public String getCode(){
        return this.code;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

    public String getDetails(){
        return this.details;
    }

    public void setHttpStatus(int httpStatus){
        this.httpStatus = httpStatus;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDetails(String details){
        this.details = details;
    }

}
