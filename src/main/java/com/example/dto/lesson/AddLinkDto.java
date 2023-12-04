package com.example.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddLinkDto {
    @NotNull
    @NotBlank
    private Long id;

    @NotNull
    @NotBlank
    private String link;
}
