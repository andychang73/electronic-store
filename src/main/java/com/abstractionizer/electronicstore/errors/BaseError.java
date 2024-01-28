package com.abstractionizer.electronicstore.errors;

public interface BaseError {

    int getHttpStatus();

    String getCode();

    String getMessage();
}
