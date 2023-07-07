package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseId(Long courseId);
    int countByCourseId(Long courseId);
}
