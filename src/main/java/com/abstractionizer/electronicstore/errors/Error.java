package com.abstractionizer.electronicstore.errors;

public enum Error implements BaseError{

    INTERNAL_SERVER_ERROR(500, "000001", "Internal server error"),
    BAD_REQUEST_ERROR(400, "000002", "Bad Request"),
    AUTH_ERROR(401, "000003", "Authentication error"),
    DATA_NOT_FOUND(400, "000004", "Data not found"),
    DATA_IS_CREATED(400, "000005", "Data already exists"),
    CREATE_DATA_FAIL(400, "000006", "Create data failed"),
    UPDATE_DATA_FAIL(400, "000007", "Update data failed"),
    DELETE_DATA_FAIL(400, "000008", "Delete data failed"),
    HTTP_METHOD_NOT_ALLOWED(405, "000009", "Http method not allowed"),

    INVALID_ACCOUNT_OR_PASSWORD(400, "000010", "Invalid account or password")
    ;

    private final int httpStatus;
    private final String code;
    private final String message;

    Error(int httpStatus, String code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
