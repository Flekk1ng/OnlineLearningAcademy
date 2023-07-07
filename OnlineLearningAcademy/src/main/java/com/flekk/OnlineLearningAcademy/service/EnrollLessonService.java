package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.model.EnrollLesson;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollLessonService {
    void enrollStudentToLesson(Long studentId, Long lessonId);
    List<LessonDto> findEnrollLessonsByStudentId(Long studentId);
    List<EnrollLesson> findEnrollLessonsByTeacherId(Long teacherId);
    int countByStudentIdAndCourseId(Long studentId, Long courseId);
}
