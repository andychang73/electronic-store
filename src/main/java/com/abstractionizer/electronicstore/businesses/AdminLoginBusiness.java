package com.abstractionizer.electronicstore.businesses;

import com.abstractionizer.electronicstore.model.login.LoginDto;

public interface AdminLoginBusiness {

    String login(LoginDto dto);
}
