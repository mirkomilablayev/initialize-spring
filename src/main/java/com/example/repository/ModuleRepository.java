package com.example.repository;

import com.example.entity.Module;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByCourseId(@NotBlank Long courseId);
}
