package com.example.repository;

import com.example.entity.Lesson;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByModuleId(@NotNull Long moduleId);
}
