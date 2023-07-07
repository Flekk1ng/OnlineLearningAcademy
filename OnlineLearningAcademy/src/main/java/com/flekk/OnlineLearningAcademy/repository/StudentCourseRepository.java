package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findByStudentId(Long studentId);
    List<StudentCourse> findByCourseId(Long courseId);
    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);
    Long countByCourseId(Long courseId);
}
