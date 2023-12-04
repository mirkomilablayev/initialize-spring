package com.example.service;

import com.example.dto.CommonResponse;
import com.example.dto.course.*;
import com.example.dto.lesson.*;
import com.example.dto.module.DeleteModuleDTO;
import com.example.dto.module.EditModuleDTO;
import com.example.dto.module.CreateModuleDto;
import com.example.dto.module.ModuleDTO;
import com.example.entity.Course;
import com.example.entity.Lesson;
import com.example.entity.Module;
import com.example.entity.User;
import com.example.exceptions.GenericException;
import com.example.repository.CourseBuyerRepository;
import com.example.repository.CourseRepository;
import com.example.repository.LessonRepository;
import com.example.repository.ModuleRepository;
import com.example.util.SecurityUtil;
import com.example.util.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseBuyerRepository courseBuyerRepository;

    private final FileService fileService;


    //create course
    public ResponseEntity<CommonResponse> createCourse(CreateCourseDTO createDTO) {
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
                    courseDTO.setCoursePicture(course.getCoursePictureId());
                    return courseDTO;
                }).toList();
        return ResponseEntity.ok(new CommonResponse(courseDTOList));
    }


    public ResponseEntity<CommonResponse> getOneCourse(Long courseId) {
        User user = SecurityUtil.getAuthorizedUser();
        String userStatus;
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Course not found!"));
        if (user.getId() == null) {
            userStatus = UserStatus.GUESS_USER;
        } else if (courseBuyerRepository.existsByCourseIdAndUserId(course.getId(), user.getId()))
            userStatus = UserStatus.AUTHORIZED_CLIENT_USER;
        else userStatus = UserStatus.AUTHORIZED_USER;


        List<ModuleDTO> moduleDTOList = moduleRepository.findAllByCourseId(courseId).stream().map(module -> {
            List<LessonDto> list = lessonRepository.findAllByModuleId(module.getId()).stream().map(lesson -> new LessonDto(lesson.getId(), lesson.getName(), userStatus.equals(UserStatus.AUTHORIZED_CLIENT_USER))).toList();

            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setId(moduleDTO.getId());
            moduleDTO.setName(module.getName());
            moduleDTO.setCourseId(courseId);
            moduleDTO.setList(list);
            return moduleDTO;
        }).toList();

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setCoursePicture(course.getCoursePictureId());
        courseDTO.setPublished(course.getPublished());
        courseDTO.setModuleDTOList(moduleDTOList);
        courseDTO.setUserStatus(userStatus);
        return ResponseEntity.ok(new CommonResponse(courseDTO));
    }

    public ResponseEntity<CommonResponse> addCoverPhoto(Long courseId, MultipartFile file) {
        SecurityUtil.checkAdmin();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Course not found!"));
        Long saved = fileService.saveAttachment(file);
        if (saved == -1)
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "File not saved");
        course.setCoursePictureId(saved);
        courseRepository.save(course);
        return ResponseEntity.ok(new CommonResponse("File successfully saved!"));
    }


    public ResponseEntity<CommonResponse> deleteCourse(DeleteCourseDto deleteCourseDto) {
        SecurityUtil.checkAdmin();
        Course course = courseRepository.findById(deleteCourseDto.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Course not found!"));
        List<Module> moduleList = moduleRepository.findAllByCourseId(course.getId());
        if (!moduleList.isEmpty())
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Course cannot deleted");
        courseRepository.deleteById(course.getId());
        return ResponseEntity.ok(new CommonResponse("Successfully deleted!"));
    }


    // ===============  Module  ======================

    public ResponseEntity<CommonResponse> createModule(CreateModuleDto createModuleDto) {
        SecurityUtil.checkAdmin();
        if (!courseRepository.existsById(createModuleDto.getCourseId())) {
            throw new GenericException(HttpStatus.NOT_FOUND, "Course not found!");
        }
        Module module = new Module();
        module.setCourseId(createModuleDto.getCourseId());
        module.setName(createModuleDto.getName());
        Module savedModule = moduleRepository.save(module);
        return ResponseEntity.ok(new CommonResponse(savedModule));
    }

    public ResponseEntity<CommonResponse> editModule(EditModuleDTO editModuleDTO) {
        SecurityUtil.checkAdmin();
        Module module = moduleRepository.findById(editModuleDTO.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Module not found!"));
        if (!Objects.equals(module.getName(), editModuleDTO.getName())) {
            module.setName(editModuleDTO.getName());
        }
        Module savedModule = moduleRepository.save(module);
        return ResponseEntity.ok(new CommonResponse("\"" + savedModule.getName() + "\" is successfully saved!"));
    }

    public ResponseEntity<CommonResponse> deleteModule(DeleteModuleDTO deleteModuleDTO) {
        // TODO: 04.12.2023 needs to do
        SecurityUtil.checkAdmin();
        return null;
    }

    public ResponseEntity<CommonResponse> getAllModule() {
        SecurityUtil.checkAdmin();
        List<ModuleDTO> allModuleDTOList = moduleRepository.findAll().stream().map(module -> new ModuleDTO(module.getId(), module.getName(), module.getCourseId())).toList();
        return ResponseEntity.ok(new CommonResponse(allModuleDTOList));
    }


    //=====================  lesson  ========================
    public ResponseEntity<CommonResponse> createLesson(CreateLessonDto createLessonDto) {
        SecurityUtil.checkAdmin();

        Module module = moduleRepository.findById(createLessonDto.getModuleId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Module not found, Lesson create!"));
        Lesson lesson = new Lesson();
        lesson.setName(createLessonDto.getName());
        lesson.setModuleId(module.getId());
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson is successfully created!"));
    }

    public ResponseEntity<CommonResponse> editLesson(EditLessonDto editLessonDto) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(editLessonDto.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        if (editLessonDto.getName() != null) lesson.setName(editLessonDto.getName());
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson is successfully edited!"));
    }


    public ResponseEntity<CommonResponse> deleteLesson(DeleteLessonDto deleteLessonDto) {
        SecurityUtil.checkAdmin();
        if (!lessonRepository.existsById(deleteLessonDto.getId())) {
            throw new GenericException(HttpStatus.NOT_FOUND, "Lesson not found!");
        }
        lessonRepository.deleteById(deleteLessonDto.getId());
        return ResponseEntity.ok(new CommonResponse("Lesson is successfully deleted!"));
    }


    public ResponseEntity<CommonResponse> addLink(AddLinkDto addLinkDto) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(addLinkDto.getId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        lesson.setLink(addLinkDto.getLink());
        lessonRepository.save(lesson);
        return new ResponseEntity<>(new CommonResponse("\"" + addLinkDto.getLink() + "\" is successfully added!"), HttpStatus.OK);
    }

    public ResponseEntity<CommonResponse> deleteLink(Long lessonId) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        lesson.setLink(null);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("\"" + lesson.getLink() + "\" is successfully deleted!"));
    }

    public ResponseEntity<CommonResponse> addFile(Long lessonId, MultipartFile file) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        Long saved = fileService.saveAttachment(file);
        if (saved == -1)
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "File not saved");

        lesson.setFileId(saved);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson file is successfully added!"));
    }

    public ResponseEntity<CommonResponse> deleteFile(Long lessonId) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        fileService.deleteFile(lesson.getFileId());
        lesson.setFileId(null);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson file is successfully deleted!"));
    }

    public ResponseEntity<CommonResponse> addVideo(Long lessonId, MultipartFile file) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        Long saved = fileService.saveAttachment(file);
        if (saved == -1)
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "File not saved");

        lesson.setVideoId(saved);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson file is successfully added!"));
    }

    public ResponseEntity<CommonResponse> deleteVideo(Long lessonId) {
        SecurityUtil.checkAdmin();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found when for edit it!!"));
        fileService.deleteFile(lesson.getVideoId());
        lesson.setVideoId(null);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(new CommonResponse("Lesson file is successfully deleted!"));
    }

    public ResponseEntity<CommonResponse> getOneLesson(Long lessonId) {
        User user = SecurityUtil.getAuthorizedUser();
        String userStatus;
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Lesson not found!!"));
        if (user.getId() == null) {
            userStatus = UserStatus.GUESS_USER;
        } else {
            userStatus = UserStatus.AUTHORIZED_USER;
            Module module = moduleRepository.findById(lesson.getModuleId()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Module not found!!"));
            if (courseBuyerRepository.existsByCourseIdAndUserId(module.getCourseId(), user.getId())) {
                userStatus = UserStatus.AUTHORIZED_CLIENT_USER;
            }
        }
        OneLessonDto oneLessonDto = getOneLessonDto(lesson, userStatus);
        return ResponseEntity.ok(new CommonResponse(oneLessonDto));
    }

    private static OneLessonDto getOneLessonDto(Lesson lesson, String userStatus) {
        OneLessonDto oneLessonDto = new OneLessonDto();
        oneLessonDto.setId(lesson.getId());
        oneLessonDto.setName(lesson.getName());
        oneLessonDto.setLink(lesson.getLink());
        oneLessonDto.setHasLink(lesson.getLink() != null);
        oneLessonDto.setVideoId(lesson.getVideoId());
        oneLessonDto.setHasVideo(lesson.getVideoId() != null);
        oneLessonDto.setFileId(lesson.getFileId());
        oneLessonDto.setHasFile(lesson.getFileId() != null);
        oneLessonDto.setModuleId(lesson.getModuleId());
        oneLessonDto.setUserStatus(userStatus);
        return oneLessonDto;
    }
}
