package com.example.service;

import com.example.dto.CommonResponse;
import com.example.dto.course.CourseCreateDTO;
import com.example.dto.course.CourseDTO;
import com.example.dto.course.EditCourseDTO;
import com.example.dto.course.PublishCourseDTO;
import com.example.dto.module.DeleteModuleDTO;
import com.example.dto.module.EditModuleDTO;
import com.example.dto.module.ModuleCreateDto;
import com.example.dto.module.ModuleDTO;
import com.example.entity.Course;
import com.example.entity.Module;
import com.example.exceptions.GenericException;
import com.example.repository.CourseRepository;
import com.example.repository.LessonRepository;
import com.example.repository.ModuleRepository;
import com.example.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    private final FileService fileService;


    //create course
    public ResponseEntity<CommonResponse> createCourse(CourseCreateDTO createDTO) {
        SecurityUtil.checkAdmin();
        Course course = new Course();
        course.setName(createDTO.getName());
        course.setDescription(createDTO.getDescription());
        course.setPrice(createDTO.getPrice());
        course.setPublished(false);
        Course savedCourses = courseRepository.save(course);
        return ResponseEntity.ok(new CommonResponse(savedCourses));
    }

    public ResponseEntity<CommonResponse> editCourse(EditCourseDTO editCourseDTO) {
        SecurityUtil.checkAdmin();
        Course course = courseRepository.findById(editCourseDTO.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Course not found!"));
        if (course.getName() != null && !course.getName().equals(editCourseDTO.getName())) {
            course.setName(editCourseDTO.getName());
        }

        if (course.getDescription() != null && !course.getDescription().equals(editCourseDTO.getDescription())) {
            course.setDescription(editCourseDTO.getDescription());
        }

        if (course.getPrice() != null && !course.getPrice().equals(editCourseDTO.getPrice())) {
            course.setPrice(editCourseDTO.getPrice());
        }
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.ok(new CommonResponse("\"" + savedCourse.getName() + "\" is successfully saved"));
    }

    public ResponseEntity<CommonResponse> publishCourse(PublishCourseDTO publishCourseDTO) {
        SecurityUtil.checkAdmin();
        Course course = courseRepository.findById(publishCourseDTO.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Course not found!"));
        course.setPublished(!course.getPublished());
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(new CommonResponse("Course is successfully " + (saved.getPublished() ? "published" : "unpublished")));
    }

    public ResponseEntity<CommonResponse> getAllCourse() {
        List<CourseDTO> courseDTOList = (SecurityUtil.isAdmin() ? courseRepository.findAll() : courseRepository.findAllByPublished(true))
                .stream().map(course -> {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setId(course.getId());
                    courseDTO.setName(course.getName());
                    courseDTO.setDescription(course.getDescription());
                    courseDTO.setPrice(course.getPrice());
                    courseDTO.setPublished(course.getPublished());
                    courseDTO.setCoursePicture(course.getCoursePicture());
                    return courseDTO;
                }).toList();
        return ResponseEntity.ok(new CommonResponse(courseDTOList));
    }

    public ResponseEntity<CommonResponse> addCoverPhoto() {
        return null;
    }


    public ResponseEntity<CommonResponse> deleteCourse() {
        return null;
    }


    public ResponseEntity<CommonResponse> getOneCourse() {
        return null;
    }

    // ===============  Module  ======================

    public ResponseEntity<CommonResponse> createModule(ModuleCreateDto moduleCreateDto) {
        SecurityUtil.checkAdmin();
        if (courseRepository.existsById(moduleCreateDto.getCourseId())) {
            throw new GenericException(HttpStatus.NOT_FOUND, "Course not found!");
        }
        Module module = new Module();
        module.setCourseId(moduleCreateDto.getCourseId());
        module.setName(module.getName());
        Module savedModule = moduleRepository.save(module);
        return ResponseEntity.ok(new CommonResponse(savedModule));
    }

    public ResponseEntity<CommonResponse> editModule(EditModuleDTO editModuleDTO) {
        SecurityUtil.checkAdmin();
        Module module = moduleRepository.findById(editModuleDTO.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Module not found!"));
        if (module.getName() != null && !module.getName().equals(editModuleDTO.getName())) {
            module.setName(editModuleDTO.getName());
        }
        Module savedModule = moduleRepository.save(module);
        return ResponseEntity.ok(new CommonResponse("\"" + savedModule.getName() + "\" is successfully saved!"));
    }

    public ResponseEntity<CommonResponse> deleteModule(DeleteModuleDTO deleteModuleDTO) {
        SecurityUtil.checkAdmin();
        return null;
    }

    public ResponseEntity<CommonResponse> getAllModule() {
        SecurityUtil.checkAdmin();
        List<ModuleDTO> moduleDTOList = moduleRepository.findAll().stream().map(module -> new ModuleDTO(module.getId(), module.getName(), module.getCourseId())).toList();
        return ResponseEntity.ok(new CommonResponse(moduleDTOList));
    }


    //=====================  lesson  ========================
    public ResponseEntity<CommonResponse> createLesson() {
        return null;
    }

    public ResponseEntity<CommonResponse> editLesson() {
        return null;
    }



    public ResponseEntity<CommonResponse> deleteLesson() {
        return null;
    }

    public ResponseEntity<CommonResponse> getOneLesson() {
        return null;
    }

    public ResponseEntity<CommonResponse> addLink() {
        return null;
    }

    public ResponseEntity<CommonResponse> deleteLink() {
        return null;
    }

    public ResponseEntity<CommonResponse> addFile() {
        return null;
    }

    public ResponseEntity<CommonResponse> deleteFile() {
        return null;
    }

    public ResponseEntity<CommonResponse> addVideo() {
        return null;
    }

    public ResponseEntity<CommonResponse> deleteVideo() {
        return null;
    }
}
