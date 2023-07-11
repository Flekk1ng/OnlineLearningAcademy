package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.*;
import com.flekk.OnlineLearningAcademy.Dto.view.AreaOfStudyCoursesDto;
import com.flekk.OnlineLearningAcademy.Dto.view.AreaOfStudyTeachersDto;
import com.flekk.OnlineLearningAcademy.Dto.view.CourseAttendanceDto;
import com.flekk.OnlineLearningAcademy.model.Course;
import com.flekk.OnlineLearningAcademy.model.EnrollLesson;
import com.flekk.OnlineLearningAcademy.model.Lesson;
import com.flekk.OnlineLearningAcademy.model.User;
import com.flekk.OnlineLearningAcademy.service.*;
import com.flekk.OnlineLearningAcademy.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final AreaOfStudyService areaOfStudyService;
    private final CourseService courseService;
    private final TeacherCourseService teacherCourseService;
    private final StudentCourseService studentCourseService;
    private final EnrollLessonService enrollLessonService;
    private final AuthenticationService authenticationService;
    private final LessonService lessonService;

    @Autowired
    public ReportController(AreaOfStudyService areaOfStudyService, CourseService courseService,
                            TeacherCourseService teacherCourseService, StudentCourseService studentCourseService,
                            EnrollLessonService enrollLessonService, AuthenticationService authenticationService,
                            LessonService lessonService) {
        this.areaOfStudyService = areaOfStudyService;
        this.courseService = courseService;
        this.teacherCourseService = teacherCourseService;
        this.enrollLessonService = enrollLessonService;
        this.authenticationService = authenticationService;
        this.lessonService = lessonService;
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('report:admin')")
    public String showAdminReport(Model model) {
        List<AreaOfStudyTeachersDto> areaOfStudyTeachersList = new ArrayList<>();
        List<AreaOfStudyCoursesDto> areaOfStudyCoursesList = new ArrayList<>();

        List<AreaOfStudyDto> areaOfStudyList = areaOfStudyService.findAllAreasOfStudy();
        for (AreaOfStudyDto areaOfStudy : areaOfStudyList) {
            AreaOfStudyTeachersDto areaOfStudyTeachers = new AreaOfStudyTeachersDto();
            AreaOfStudyCoursesDto areaOfStudyCourses = new AreaOfStudyCoursesDto();
            areaOfStudyTeachers.setAreaOfStudyName(areaOfStudy.getName());
            areaOfStudyCourses.setAreaOfStudyName(areaOfStudy.getName());

            List<CourseDto> courses = courseService.findByAreaOfStudyId(areaOfStudy.getId());

            List<UserDto> teachers = courses.stream()
                    .map(course -> teacherCourseService.findTeachersByCourseId(course.getId()))
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());

            areaOfStudyCourses.setCourses(courses);
            areaOfStudyCoursesList.add(areaOfStudyCourses);

            areaOfStudyTeachers.setTeachers(teachers);
            areaOfStudyTeachersList.add(areaOfStudyTeachers);
        }
        model.addAttribute("areaOfStudyTeachersList", areaOfStudyTeachersList);
        model.addAttribute("areaOfStudyCoursesList", areaOfStudyCoursesList);

        List<CourseAttendanceDto> topCourses = courseService.findTopCoursesByAttendance();
        model.addAttribute("topCourses", topCourses);

        return "report_admin";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasAuthority('report:teacher')")
    public String showTeacherReport(Model model) {
        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto teacher = currentUser.get();

            List<EnrollLesson> enrollLessons = enrollLessonService.findEnrollLessonsByTeacherId(teacher.getId());

            Map<CourseDto, Map<UserDto, Set<LessonDto>>> courseStudentEnrollLessons = new HashMap<>();
            for (EnrollLesson enrollLesson : enrollLessons) {
                Course course = enrollLesson.getLesson().getCourse();
                User student = enrollLesson.getStudent();
                Lesson lesson = enrollLesson.getLesson();

                CourseDto courseDto = new CourseDto(course.getId(), course.getName(), course.getDescription(),
                        course.getAreaOfStudy().getId());
                UserDto studentDto = new UserDto(student.getId(), student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getRole().name());
                LessonDto lessonDto = new LessonDto(lesson.getId(), lesson.getTopic(), lesson.getContent(),
                        DateTimeUtils.format(lesson.getDate()), lesson.getStatus(), lesson.getCourse().getId());

                Map<UserDto, Set<LessonDto>> studentEnrollLessons = courseStudentEnrollLessons.computeIfAbsent(courseDto, k -> new HashMap<>());
                Set<LessonDto> studentEnrollLessonList = studentEnrollLessons.computeIfAbsent(studentDto, k -> new HashSet<>());
                studentEnrollLessonList.add(lessonDto);
            }
            model.addAttribute("courseStudentEnrollLessons", courseStudentEnrollLessons);
        }
        return "report_teacher";
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('report:student')")
    public String showStudentReport(Model model) {
        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            Long studentId = student.getId();

            List<CourseDto> courses = studentCourseService.findCoursesByStudentId(studentId);
            Map<String, Double> courseAttendances = new HashMap<>();
            for (CourseDto course : courses) {
                int totalLessons = lessonService.countLessonsByCourseId(course.getId());

                int attendedLessons = enrollLessonService.countByStudentIdAndCourseId(studentId, course.getId());

                double attendancePercentage = (totalLessons == 0) ? -1 : (double) attendedLessons / totalLessons * 100;
                courseAttendances.put(course.getName(), attendancePercentage);
            }
            model.addAttribute("courseAttendances", courseAttendances);
        }
        return "report_student";
    }

}
