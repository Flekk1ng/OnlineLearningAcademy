package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.model.*;
import com.flekk.OnlineLearningAcademy.repository.CourseRepository;
import com.flekk.OnlineLearningAcademy.repository.StudentCourseRepository;
import com.flekk.OnlineLearningAcademy.repository.UserRepository;
import com.flekk.OnlineLearningAcademy.service.StudentCourseService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    private static final Logger logger = LogManager.getLogger(StudentCourseServiceImpl.class);
    private final StudentCourseRepository studentCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentCourseServiceImpl(StudentCourseRepository studentCourseRepository,
                                    UserRepository userRepository, CourseRepository courseRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void subscribeStudentToCourse(Long studentId, Long courseId) {
        logger.info("Subscribing student {} to course {}", studentId, courseId);
        try {
            Optional<StudentCourse> existingStudentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId);
            if (existingStudentCourse.isPresent()) {
                throw new EntityExistsException("StudentCourse already exists");
            }
            StudentCourse studentCourse = new StudentCourse();
            User student = userRepository.findByIdAndRole(studentId, Role.STUDENT).orElseThrow(()
                    -> new EntityNotFoundException("Student not found"));
            studentCourse.setStudent(student);
            Course course = courseRepository.findById(courseId).orElseThrow(()
                    -> new EntityNotFoundException("Course not found"));
            studentCourse.setCourse(course);
            studentCourseRepository.save(studentCourse);
            logger.info("Student {} subscribed to course {} successfully", studentId, courseId);
        } catch (Exception e) {
            logger.error("Error subscribing student {} to course {}: {}", studentId, courseId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        logger.info("Removing student {} from course {}", studentId, courseId);
        try {
            StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(()
                    -> new EntityNotFoundException("Subscription not found"));
            studentCourseRepository.delete(studentCourse);
            logger.info("Student {} removes from course {} successfully", studentId, courseId);
        } catch (Exception e) {
            logger.error("Error removing student {} from course {}: {}", studentId, courseId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CourseDto> findCoursesByStudentId(Long studentId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        return studentCourses.stream()
                .map(StudentCourse::getCourse)
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getDescription(), course.getAreaOfStudy().getId())).toList();
    }

    @Override
    public List<UserDto> findStudentsByCourseId(Long courseId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByCourseId(courseId);
        return studentCourses.stream()
                .map(StudentCourse::getStudent)
                .map(student -> new UserDto(student.getId(), student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getRole().name())).toList();
    }

    @Override
    public Long findCountStudentsByCourseId(Long courseId) {
        return studentCourseRepository.countByCourseId(courseId);
    }
}
