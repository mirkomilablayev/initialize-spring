package com.example.dto.course;


import com.example.dto.module.ModuleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String userStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ModuleDTO> moduleDTOList;

}
