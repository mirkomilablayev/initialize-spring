package com.example.dto.module;

import com.example.dto.lesson.LessonDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModuleDTO {
    private Long id;
    private String name;
    private Long courseId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<LessonDto> list;



    public ModuleDTO(Long id, String name, Long courseId) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
    }
}
