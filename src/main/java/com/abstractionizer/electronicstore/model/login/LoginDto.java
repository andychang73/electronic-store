package com.abstractionizer.electronicstore.model.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
