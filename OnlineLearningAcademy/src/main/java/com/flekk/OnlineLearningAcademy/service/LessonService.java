package com.flekk.OnlineLearningAcademy.service;


import com.flekk.OnlineLearningAcademy.Dto.LessonDto;

import java.util.List;

public interface LessonService {
    void createLesson(LessonDto lessonDto);
    void updateLesson(LessonDto lessonDto);
    void deleteById(Long id);
    LessonDto findById(Long id);
    List<LessonDto> findByCourseId(Long courseId);
    int countLessonsByCourseId(Long courseId);
}
