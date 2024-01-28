package com.abstractionizer.electronicstore.service;

public interface LoginService {

    void verifyPasswordOrThrow(String inputPassword, String dbPassword);


}
