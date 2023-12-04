package com.example.repository;

import com.example.entity.CourseBuyer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBuyerRepository extends JpaRepository<CourseBuyer, Long> {
    Boolean existsByCourseIdAndUserId(@NotNull @NotBlank Long courseId, @NotNull @NotBlank Long userId);
}
