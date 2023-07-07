package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.model.*;
import com.flekk.OnlineLearningAcademy.repository.*;
import com.flekk.OnlineLearningAcademy.service.EnrollLessonService;
import com.flekk.OnlineLearningAcademy.util.DateTimeUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollLessonServiceImpl implements EnrollLessonService {
    private static final Logger logger = LogManager.getLogger(EnrollLessonServiceImpl.class);
    private final EnrollLessonRepository enrollLessonRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public EnrollLessonServiceImpl(EnrollLessonRepository enrollLessonRepository,
                                    UserRepository userRepository, LessonRepository lessonRepository) {
        this.enrollLessonRepository = enrollLessonRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }


    @Override
    public void enrollStudentToLesson(Long studentId, Long lessonId) {
        logger.info("Enrolling student {} to lesson {}", studentId, lessonId);
        try {
            Optional<EnrollLesson> existingEnrollLesson = enrollLessonRepository.findByStudentIdAndLessonId(studentId, lessonId);
            if (existingEnrollLesson.isPresent()) {
                throw new EntityExistsException("EnrollLesson already exists");
            }
            EnrollLesson enrollLesson = new EnrollLesson();
            User student = userRepository.findByIdAndRole(studentId, Role.STUDENT).orElseThrow(()
                    -> new EntityNotFoundException("Student not found"));
            enrollLesson.setStudent(student);
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()
                    -> new EntityNotFoundException("Lesson not found"));
            enrollLesson.setLesson(lesson);
            enrollLessonRepository.save(enrollLesson);
            logger.info("Student {} enrolled to lesson {} successfully", studentId, lessonId);
        } catch (Exception e) {
            logger.error("Error enrolling student {} to lesson {}: {}", studentId, lessonId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<LessonDto> findEnrollLessonsByStudentId(Long studentId) {
        List<EnrollLesson> enrollLessons = enrollLessonRepository.findByStudentId(studentId);
        return enrollLessons.stream()
                .map(EnrollLesson::getLesson)
                .map(lesson -> new LessonDto(lesson.getId(), lesson.getTopic(), lesson.getContent(),
                        DateTimeUtils.format(lesson.getDate()), lesson.getStatus(), lesson.getCourse().getId())).toList();
    }

    @Override
    public List<EnrollLesson> findEnrollLessonsByTeacherId(Long teacherId) {
        return enrollLessonRepository.findEnrollLessonsByTeacherId(teacherId);
    }

    @Override
    public int countByStudentIdAndCourseId(Long studentId, Long courseId) {
        return enrollLessonRepository.countByStudentIdAndLessonCourseId(studentId, courseId);
    }
}
