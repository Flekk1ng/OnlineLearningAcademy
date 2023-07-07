package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.view.CourseAttendanceDto;

import java.util.List;

public interface CourseService {
    void createCourse(CourseDto courseDto);
    void updateCourse(CourseDto courseDto);
    void deleteById(Long id);
    List<CourseDto> findAllCourses();
    CourseDto findById(Long id);
    List<CourseDto> findByAreaOfStudy(AreaOfStudyDto areaOfStudyDto);
    List<CourseDto> findByAreaOfStudyId(Long areaOfStudyId);
    List<CourseAttendanceDto> findTopCoursesByAttendance();
}
