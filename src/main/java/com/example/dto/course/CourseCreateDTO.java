package com.example.dto.course;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseCreateDTO {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotBlank
    private Long price;

}
