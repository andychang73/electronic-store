package com.abstractionizer.electronicstore.constant;

import lombok.NonNull;

public class RedisKey {

    private static final String R_KEY_ADMIN_TOKEN = "ADMIN::TOKEN::%s";
    private static final String R_KEY_CREATE_PRODUCT = "CREATE::PRODUCT";

    public static String getRKeyAdminToken(@NonNull final String token){
        return String.format(R_KEY_ADMIN_TOKEN, token);
    }

    public static String getRKeyCreateProduct(){
        return R_KEY_CREATE_PRODUCT;
    }
}
