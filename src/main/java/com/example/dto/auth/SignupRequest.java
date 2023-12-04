package com.example.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull
    @Max(30)
    @Min(2)
    private String fullName;
    @NotBlank
    @Min(5)
    @Max(15)
    private String username;
    @Min(5)
    @Max(15)
    @NotBlank
    private String password;

    private String about;
}
