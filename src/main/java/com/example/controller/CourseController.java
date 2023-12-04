package com.example.controller;

import com.example.dto.CommonResponse;
import com.example.dto.course.CourseCreateDTO;
import com.example.dto.course.EditCourseDTO;
import com.example.dto.course.PublishCourseDTO;
import com.example.dto.module.DeleteModuleDTO;
import com.example.dto.module.EditModuleDTO;
import com.example.dto.module.ModuleCreateDto;
import com.example.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "COURSE-CONTROLLER", description = "Course management controller")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @PostMapping("/course/create")
    public ResponseEntity<CommonResponse> createCourse(@RequestBody CourseCreateDTO createDTO) {
        return courseService.createCourse(createDTO);
    }

    @PutMapping("/course/edit")
    public ResponseEntity<CommonResponse> editCourse(@RequestBody EditCourseDTO editCourseDTO) {
        return courseService.editCourse(editCourseDTO);
    }

    @PutMapping("/course/publish")
    public ResponseEntity<CommonResponse> publish(@RequestBody PublishCourseDTO publishCourseDTO) {
        return courseService.publishCourse(publishCourseDTO);
    }

    @PutMapping("/course/addCover")
    public ResponseEntity<CommonResponse> addCoverPhoto() {
        return courseService.addCoverPhoto();
    }

    @DeleteMapping("/course/delete")
    public ResponseEntity<CommonResponse> deleteCourse() {
        return courseService.deleteCourse();
    }

    @GetMapping("/course/all")
    public ResponseEntity<CommonResponse> getAllCourse() {
        return courseService.getAllCourse();
    }


    @GetMapping("/course/one")
    public ResponseEntity<CommonResponse> getOneCourse() {
        return courseService.getOneCourse();
    }


    //======================= module ===============================

    @PostMapping("/module/create")
    public ResponseEntity<CommonResponse> createModule(@RequestBody ModuleCreateDto moduleCreateDto) {
        return courseService.createModule(moduleCreateDto);
    }

    @PutMapping("/module/edit")
    public ResponseEntity<CommonResponse> editModule(@RequestBody EditModuleDTO editModuleDTO) {
        return courseService.editModule(editModuleDTO);
    }

    @DeleteMapping("/module/delete")
    public ResponseEntity<CommonResponse> deleteModule(@RequestBody DeleteModuleDTO deleteModuleDTO) {
        return courseService.deleteModule(deleteModuleDTO);
    }

    @GetMapping("/module/all")
    public ResponseEntity<CommonResponse> getAllModule() {
        return courseService.getAllModule();
    }

    //====================== Lesson ================================

    @PostMapping("/lesson/create")
    public ResponseEntity<CommonResponse> createLesson() {
        return courseService.createLesson();
    }

    @PutMapping("/lesson/edit")
    public ResponseEntity<CommonResponse> editLesson() {
        return courseService.editLesson();
    }

    @DeleteMapping("/lesson/delete")
    public ResponseEntity<CommonResponse> deleteLesson() {
        return courseService.deleteLesson();
    }

    @GetMapping("/lesson/one")
    public ResponseEntity<CommonResponse> getOneLesson() {
        return courseService.getOneLesson();
    }

    @PostMapping("/lesson/add-link")
    public ResponseEntity<CommonResponse> addLink() {
        return courseService.addLink();
    }

    @PostMapping("/lesson/delete-link")
    public ResponseEntity<CommonResponse> deleteLink() {
        return courseService.deleteLink();
    }

    @PostMapping("/lesson/add-file")
    public ResponseEntity<CommonResponse> addFile() {
        return courseService.addFile();
    }

    @PostMapping("/lesson/delete-file")
    public ResponseEntity<CommonResponse> deleteFile() {
        return courseService.deleteFile();
    }

    @PostMapping("/lesson/add-video")
    public ResponseEntity<CommonResponse> addVideo() {
        return courseService.addVideo();
    }

    @PostMapping("/lesson/delete-video")
    public ResponseEntity<CommonResponse> deleteVideo() {
        return courseService.deleteVideo();
    }


}
