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
    @Size(min = 5, max = 30)
    private String username;

    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

}
