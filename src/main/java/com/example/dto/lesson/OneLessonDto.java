package com.example.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneLessonDto {
    private Long id;
    private String name;
    private String link;
    private Boolean hasLink;
    private Long videoId;
    private Boolean hasVideo;
    private Long fileId;
    private Boolean hasFile;
    private Long moduleId;
    private String userStatus;

}
