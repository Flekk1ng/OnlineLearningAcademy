package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.model.EnrollLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollLessonRepository extends JpaRepository<EnrollLesson, Long> {
    List<EnrollLesson> findByStudentId(Long studentId);
    Optional<EnrollLesson> findByStudentIdAndLessonId(Long studentId, Long lessonId);
    @Query("SELECT el " +
            "FROM EnrollLesson el " +
            "WHERE el.lesson.course IN " +
            "(SELECT tc.course FROM TeacherCourse tc WHERE tc.teacher.id = :teacherId)")
    List<EnrollLesson> findEnrollLessonsByTeacherId(@Param("teacherId") Long teacherId);
    int countByStudentIdAndLessonCourseId(Long studentId, Long courseId);
}
