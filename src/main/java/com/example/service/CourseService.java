package com.example.service;

import com.example.entity.CourseBuyer;
import com.example.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    private final FileService fileService;


    //create course
    public ResponseEntity<?> createCourse(){
        return null;
    }

    public ResponseEntity<?> editCourse(){
        return null;
    }

    public ResponseEntity<?> addCourseFile(){
        return null;
    }

    public ResponseEntity<?> publishCourse(){
        return null;
    }

    public ResponseEntity<?> deleteCourse(){
        return null;
    }

    public ResponseEntity<?> getAllCourse(){
        return null;
    }

    public ResponseEntity<?> getOneCourse(){
        return null;
    }

    // ===============  Module  ======================

    public ResponseEntity<?> createModule(){
        return null;
    }

    public ResponseEntity<?> editModule(){
        return null;
    }

    public ResponseEntity<?> deleteModule(){
        return null;
    }

    public ResponseEntity<?> getAllModule(){
        return null;
    }


    //=====================  lesson  ========================
    public ResponseEntity<?> createLesson(){
        return null;
    }

    public ResponseEntity<?> editLesson(){
        return null;
    }

    public ResponseEntity<?> addVideo(){
        return null;
    }

    public ResponseEntity<?> deleteLesson(){
        return null;
    }

    public ResponseEntity<?> getOneLesson(){
        return null;
    }



}
