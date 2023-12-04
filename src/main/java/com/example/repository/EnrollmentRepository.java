package com.example.repository;

import com.example.entity.Enrollment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Boolean existsByCourseIdAndUserId(@NotNull @NotBlank Long courseId, @NotNull @NotBlank Long userId);
}
