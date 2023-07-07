package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.Dto.view.CourseAttendanceDto;
import com.flekk.OnlineLearningAcademy.model.AreaOfStudy;
import com.flekk.OnlineLearningAcademy.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAreaOfStudy(AreaOfStudy areaOfStudy);
    List<Course> findByAreaOfStudyId(Long areaOfStudyId);
    @Query("SELECT new com.flekk.OnlineLearningAcademy.Dto.view.CourseAttendanceDto(c.name, COUNT(el)) " +
            "FROM EnrollLesson el JOIN el.lesson l JOIN l.course c " +
            "GROUP BY c.name " +
            "ORDER BY COUNT(el) DESC")
    List<CourseAttendanceDto> findTopCoursesByAttendance();
}
