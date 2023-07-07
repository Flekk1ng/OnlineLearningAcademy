package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.model.*;
import com.flekk.OnlineLearningAcademy.repository.CourseRepository;
import com.flekk.OnlineLearningAcademy.repository.TeacherCourseRepository;
import com.flekk.OnlineLearningAcademy.repository.UserRepository;
import com.flekk.OnlineLearningAcademy.service.TeacherCourseService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {
    private static final Logger logger = LogManager.getLogger(TeacherCourseServiceImpl.class);
    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TeacherCourseServiceImpl(TeacherCourseRepository teacherCourseRepository,
                                    UserRepository userRepository, CourseRepository courseRepository) {
        this.teacherCourseRepository = teacherCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void addTeacherToCourse(Long teacherId, Long courseId) {
        logger.info("Adding teacher {} to course {}", teacherId, courseId);
        try {
            TeacherCourse teacherCourse = new TeacherCourse();
            User teacher = userRepository.findByIdAndRole(teacherId, Role.TEACHER).orElseThrow(()
                    -> new EntityNotFoundException("Teacher not found"));
            teacherCourse.setTeacher(teacher);
            Course course = courseRepository.findById(courseId).orElseThrow(()
                    -> new EntityNotFoundException("Course not found"));
            teacherCourse.setCourse(course);
            teacherCourseRepository.save(teacherCourse);
            logger.info("Teacher {} added to course {} successfully", teacherId, courseId);
        } catch (Exception e) {
            logger.error("Error adding teacher {} to course {}: {}", teacherId, courseId, e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean isTeacherOnCourse(Long teacherId, Long courseId) {
        return teacherCourseRepository.existsByTeacherIdAndCourseId(teacherId, courseId);
    }

    @Override
    public List<UserDto> findTeachersByCourseId(Long courseId) {
        List<TeacherCourse> teacherCourses = teacherCourseRepository.findDistinctByCourseId(courseId);
        return teacherCourses.stream()
                .map(TeacherCourse::getTeacher)
                .map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().name())).toList();
    }
}
