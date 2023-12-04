package com.example.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonDto {
    private Long id;
    private String name;
    private Boolean isVisible = false;
}
