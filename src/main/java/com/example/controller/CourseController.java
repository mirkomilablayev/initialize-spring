package com.example.controller;

import com.example.dto.CommonResponse;
import com.example.dto.course.CreateCourseDTO;
import com.example.dto.course.DeleteCourseDto;
import com.example.dto.course.EditCourseDTO;
import com.example.dto.course.PublishCourseDTO;
import com.example.dto.lesson.AddLinkDto;
import com.example.dto.lesson.DeleteLessonDto;
import com.example.dto.lesson.EditLessonDto;
import com.example.dto.lesson.CreateLessonDto;
import com.example.dto.module.DeleteModuleDTO;
import com.example.dto.module.EditModuleDTO;
import com.example.dto.module.CreateModuleDto;
import com.example.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "COURSE-CONTROLLER", description = "Course management controller")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @PostMapping("/course/create")
    public ResponseEntity<CommonResponse> createCourse(@RequestBody CreateCourseDTO createDTO) {
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
    public ResponseEntity<CommonResponse> addCoverPhoto(@RequestParam Long courseId,@RequestParam("file") MultipartFile file) {
        return courseService.addCoverPhoto(courseId, file);
    }

    @DeleteMapping("/course/delete")
    public ResponseEntity<CommonResponse> deleteCourse(@RequestBody DeleteCourseDto deleteCourseDto) {
        return courseService.deleteCourse(deleteCourseDto);
    }

    @GetMapping("/course/all")
    public ResponseEntity<CommonResponse> getAllCourse() {
        return courseService.getAllCourse();
    }


    @GetMapping("/course/one")
    public ResponseEntity<CommonResponse> getOneCourse(@RequestParam Long courseId) {
        return courseService.getOneCourse(courseId);
    }


    //======================= module ===============================

    @PostMapping("/module/create")
    public ResponseEntity<CommonResponse> createModule(@RequestBody CreateModuleDto createModuleDto) {
        return courseService.createModule(createModuleDto);
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
    public ResponseEntity<CommonResponse> createLesson(@RequestBody CreateLessonDto createLessonDto) {
        return courseService.createLesson(createLessonDto);
    }

    @PutMapping("/lesson/edit")
    public ResponseEntity<CommonResponse> editLesson(@RequestBody EditLessonDto editLessonDto) {
        return courseService.editLesson(editLessonDto);
    }

    @GetMapping("/lesson/one")
    public ResponseEntity<CommonResponse> getOneLesson(@RequestParam Long lessonId) {
        return courseService.getOneLesson(lessonId);
    }
    @DeleteMapping("/lesson/delete")
    public ResponseEntity<CommonResponse> deleteLesson(@RequestBody DeleteLessonDto deleteLessonDto) {
        return courseService.deleteLesson(deleteLessonDto);
    }
    @PutMapping("/lesson/add-link")
    public ResponseEntity<CommonResponse> addLink(@RequestBody AddLinkDto addLinkDto) {
        return courseService.addLink(addLinkDto);
    }

    @DeleteMapping("/lesson/delete-link/{lessonId}")
    public ResponseEntity<CommonResponse> deleteLink(@PathVariable Long lessonId) {
        return courseService.deleteLink(lessonId);
    }

    @PutMapping("/lesson/add-file")
    public ResponseEntity<CommonResponse> addFile(@RequestParam Long lessonId, @RequestParam("file") MultipartFile file) {
        return courseService.addFile(lessonId, file);
    }

    @DeleteMapping("/lesson/delete-file")
    public ResponseEntity<CommonResponse> deleteFile(@RequestParam Long courseId) {
        return courseService.deleteFile(courseId);
    }

    @PutMapping("/lesson/add-video")
    public ResponseEntity<CommonResponse> addVideo(@RequestParam Long lessonId, @RequestParam("file") MultipartFile file) {
        return courseService.addVideo(lessonId, file);
    }

    @DeleteMapping("/lesson/delete-video")
    public ResponseEntity<CommonResponse> deleteVideo(@RequestParam Long lessonId) {
        return courseService.deleteVideo(lessonId);
    }


}
