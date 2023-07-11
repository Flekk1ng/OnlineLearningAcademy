package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.UserDto;

import java.util.List;

public interface TeacherCourseService {
    void addTeacherToCourse(Long teacherId, Long courseId);
    boolean isTeacherOnCourse(Long teacherId, Long courseId);
    List<UserDto> findTeachersByCourseId(Long courseId);
}
