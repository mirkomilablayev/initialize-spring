package com.example.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotEmpty
    private String username;
    @NotBlank
    private String password;

}
