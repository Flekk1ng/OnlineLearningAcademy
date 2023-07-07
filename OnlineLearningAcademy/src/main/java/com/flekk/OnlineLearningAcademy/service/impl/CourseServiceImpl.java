package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.view.CourseAttendanceDto;
import com.flekk.OnlineLearningAcademy.model.AreaOfStudy;
import com.flekk.OnlineLearningAcademy.model.Course;
import com.flekk.OnlineLearningAcademy.repository.AreaOfStudyRepository;
import com.flekk.OnlineLearningAcademy.repository.CourseRepository;
import com.flekk.OnlineLearningAcademy.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);
    private final CourseRepository courseRepository;
    private final AreaOfStudyRepository areaOfStudyRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, AreaOfStudyRepository areaOfStudyRepository) {
        this.courseRepository = courseRepository;
        this.areaOfStudyRepository = areaOfStudyRepository;
    }
    @Override
    public void createCourse(CourseDto courseDto) {
        logger.info("Creating course {}", courseDto.getName());
        try {
            Course course = new Course();
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(courseDto.getAreaOfStudyId()).orElseThrow(()
                    -> new EntityNotFoundException("Area of study not found"));
            course.setAreaOfStudy(areaOfStudy);
            courseRepository.save(course);
            logger.info("Course {} created successfully", courseDto.getName());
        } catch (Exception e) {
            logger.error("Error creating course {}: {}", courseDto.getName(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        logger.info("Updating course {}", courseDto.getId());
        try {
            Course course = courseRepository.findById(courseDto.getId()).orElseThrow(()
                    -> new EntityNotFoundException("Course not found"));
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            courseRepository.save(course);
            logger.info("Course {} updated successfully", courseDto.getId());
        } catch (Exception e) {
            logger.error("Error updating course {}: {}", courseDto.getId(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting course {}", id);
        try {
            courseRepository.deleteById(id);
            logger.info("Course {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error updating course {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CourseDto> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Course not found"));
        return convertToDto(course);
    }

    @Override
    public List<CourseDto> findByAreaOfStudy(AreaOfStudyDto areaOfStudyDto) {
        List<Course> courses = courseRepository.findByAreaOfStudyId(areaOfStudyDto.getId());
        return courses.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findByAreaOfStudyId(Long areaOfStudyId) {
        List<Course> courses = courseRepository.findByAreaOfStudyId(areaOfStudyId);
        return courses.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseAttendanceDto> findTopCoursesByAttendance() {
        return courseRepository.findTopCoursesByAttendance();
    }

    private CourseDto convertToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription());
        courseDto.setAreaOfStudyId(course.getAreaOfStudy().getId());
        return courseDto;
    }
}
