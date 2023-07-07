package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.model.Course;
import com.flekk.OnlineLearningAcademy.model.Lesson;
import com.flekk.OnlineLearningAcademy.repository.CourseRepository;
import com.flekk.OnlineLearningAcademy.repository.LessonRepository;
import com.flekk.OnlineLearningAcademy.service.LessonService;
import com.flekk.OnlineLearningAcademy.util.DateTimeUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {
    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createLesson(LessonDto lessonDto) {
        logger.info("Creating lesson {}", lessonDto.getTopic());
        try {
            Lesson lesson = new Lesson();
            lesson.setTopic(lessonDto.getTopic());
            lesson.setContent(lessonDto.getContent());
            lesson.setDate(DateTimeUtils.parse(lessonDto.getDate()));
            lesson.setStatus(lessonDto.getStatus());
            Course course = courseRepository.findById(lessonDto.getCourseId()).orElseThrow(()
                    -> new EntityNotFoundException("Course not found"));
            lesson.setCourse(course);
            lessonRepository.save(lesson);
            logger.info("Lesson {} created successfully", lessonDto.getTopic());
        } catch (Exception e) {
            logger.error("Error creating lesson {}: {}", lessonDto.getTopic(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateLesson(LessonDto lessonDto) {
        logger.info("Updating lesson {}", lessonDto.getId());
        try {
            Lesson lesson = lessonRepository.findById(lessonDto.getId()).orElseThrow(()
                    -> new EntityNotFoundException("Lesson not found"));
            lesson.setTopic(lessonDto.getTopic());
            lesson.setContent(lessonDto.getContent());
            lesson.setDate(DateTimeUtils.parse(lessonDto.getDate()));
            lesson.setStatus(lessonDto.getStatus());
            lessonRepository.save(lesson);
            logger.info("Lesson {} updated successfully", lessonDto.getId());
        } catch (Exception e) {
            logger.error("Error updating lesson {}: {}", lessonDto.getId(), e.getMessage());
            throw e;
        }
    }


    @Override
    public void deleteById(Long id) {
        logger.info("Deleting lesson {}", id);
        try {
            lessonRepository.deleteById(id);
            logger.info("Lesson {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting lesson {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public LessonDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Lesson not found"));
        return convertToDto(lesson);
    }

    @Override
    public List<LessonDto> findByCourseId(Long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int countLessonsByCourseId(Long courseId) {
        return lessonRepository.countByCourseId(courseId);
    }

    private LessonDto convertToDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTopic(lesson.getTopic());
        lessonDto.setContent(lesson.getContent());
        lessonDto.setDate(DateTimeUtils.format(lesson.getDate()));
        lessonDto.setStatus(lesson.getStatus());
        lessonDto.setCourseId(lesson.getCourse().getId());
        return lessonDto;
    }
}
