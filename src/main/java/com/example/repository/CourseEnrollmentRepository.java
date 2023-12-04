package com.example.repository;

import com.example.entity.CourseEnrollment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    Boolean existsByCourseIdAndUserId(@NotNull @NotBlank Long courseId, @NotNull @NotBlank Long userId);
}
