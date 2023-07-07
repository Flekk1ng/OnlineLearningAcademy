package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;

import java.util.List;

public interface StudentCourseService {
    void subscribeStudentToCourse(Long studentId, Long courseId);
    void removeStudentFromCourse(Long studentId, Long courseId);
    List<CourseDto> findCoursesByStudentId(Long studentId);
    List<UserDto> findStudentsByCourseId(Long courseId);
    Long findCountStudentsByCourseId(Long courseId);
}
