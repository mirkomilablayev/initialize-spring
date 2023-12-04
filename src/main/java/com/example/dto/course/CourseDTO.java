package com.example.dto.course;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private Long coursePicture;
    private Boolean published;
    private Long price;

}
