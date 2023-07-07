package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final AreaOfStudyService areaOfStudyService;
    private final LessonService lessonService;
    private final TeacherCourseService teacherCourseService;
    private final StudentCourseService studentCourseService;
    private final AuthenticationService authenticationService;
    private final EnrollLessonService enrollLessonService;

    @Autowired
    public CourseController(CourseService courseService, AreaOfStudyService areaOfStudyService,
                            LessonService lessonService, TeacherCourseService teacherCourseService,
                            StudentCourseService studentCourseService, AuthenticationService authenticationService,
                            EnrollLessonService enrollLessonService) {
        this.courseService = courseService;
        this.areaOfStudyService = areaOfStudyService;
        this.lessonService = lessonService;
        this.teacherCourseService = teacherCourseService;
        this.studentCourseService = studentCourseService;
        this.authenticationService = authenticationService;
        this.enrollLessonService = enrollLessonService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('courses:edit')")
    public String createCourse(@ModelAttribute CourseDto courseDto) {
        courseService.createCourse(courseDto);
        return "redirect:/course/byAreaOfStudy/edit/" + courseDto.getAreaOfStudyId();
    }

    @GetMapping("/byAreaOfStudy/edit/{areaOfStudyId}")
    @PreAuthorize("hasAuthority('courses:edit')")
    public String getCoursesByAreaOfStudyEdit(@PathVariable Long areaOfStudyId, Model model) {
        List<CourseDto> courses = courseService.findByAreaOfStudyId(areaOfStudyId);
        model.addAttribute("courses", courses);
        AreaOfStudyDto areaOfStudy = areaOfStudyService.findById(areaOfStudyId);
        model.addAttribute("areaOfStudy", areaOfStudy);
        return "courses_by_area_of_study";
    }

    @GetMapping("/byAreaOfStudy/view/{areaOfStudyId}")
    @PreAuthorize("hasAuthority('courses:enroll')")
    public String getCoursesByAreaOfStudyView(@PathVariable Long areaOfStudyId, Model model) {
        List<CourseDto> courses = courseService.findByAreaOfStudyId(areaOfStudyId);
        model.addAttribute("courses", courses);
        AreaOfStudyDto areaOfStudy = areaOfStudyService.findById(areaOfStudyId);
        model.addAttribute("areaOfStudy", areaOfStudy);

        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            Long studentId = student.getId();
            List<CourseDto> subscribedCourses = studentCourseService.findCoursesByStudentId(studentId);
            model.addAttribute("subscribedCourses", subscribedCourses);
        }
        return "courses_by_area_of_study";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('courses:edit')")
    public String CourseLessons(@PathVariable Long id, Model model) {
        List<LessonDto> lessons = lessonService.findByCourseId(id);
        model.addAttribute("lessons", lessons);
        CourseDto course = courseService.findById(id);
        model.addAttribute("course", course);
        List<UserDto> teachers = teacherCourseService.findTeachersByCourseId(id);
        String teacherNames = teachers.stream()
                .map(teacher -> teacher.getFirstName() + ' ' + teacher.getLastName())
                .collect(Collectors.joining(", "));
        model.addAttribute("teacherNames", teacherNames);
        return "course_lessons_edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('courses:edit')")
    public String editCourseLessons(@ModelAttribute CourseDto courseDto) {
        courseService.updateCourse(courseDto);
        return "redirect:/course/edit/" + courseDto.getId();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('courses:edit')")
    public String editCourseLessons(@PathVariable Long id) {
        AreaOfStudyDto areaOfStudy = areaOfStudyService.findByCourseId(id);
        courseService.deleteById(id);
        return "redirect:/course/byAreaOfStudy/edit/" + areaOfStudy.getId();
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('lessons:enroll')")
    public String viewCourseLessons(@PathVariable Long id, Model model) {
        List<LessonDto> lessons = lessonService.findByCourseId(id);
        model.addAttribute("lessons", lessons);
        CourseDto course = courseService.findById(id);
        model.addAttribute("course", course);

        List<UserDto> teachers = teacherCourseService.findTeachersByCourseId(id);
        String teacherNames = teachers.stream()
                .map(teacher -> teacher.getFirstName() + ' ' + teacher.getLastName())
                .collect(Collectors.joining(", "));

        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            Long studentId = student.getId();
            List<LessonDto> enrolledLessons = enrollLessonService.findEnrollLessonsByStudentId(studentId);
            model.addAttribute("enrolledLessons", enrolledLessons);
        }

        return "course_lessons";
    }

    @PostMapping("/subscribe/{courseId}")
    @PreAuthorize("hasAuthority('courses:enroll')")
    public String subscribe(@PathVariable Long courseId) {
        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            studentCourseService.subscribeStudentToCourse(student.getId(), courseId);
        }
        CourseDto course = courseService.findById(courseId);
        return "redirect:/course/byAreaOfStudy/view/" + course.getAreaOfStudyId();
    }

    @PostMapping("/unsubscribe/{courseId}")
    @PreAuthorize("hasAuthority('courses:enroll')")
    public String unsubscribe(@PathVariable Long courseId) {
        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            studentCourseService.removeStudentFromCourse(student.getId(), courseId);
        }
        CourseDto course = courseService.findById(courseId);
        return "redirect:/course/byAreaOfStudy/view/" + course.getAreaOfStudyId();
    }



}
