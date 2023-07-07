package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.model.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    boolean existsByTeacherIdAndCourseId(Long teacherId, Long courseId);
    List<TeacherCourse> findDistinctByCourseId(Long courseId);

}
