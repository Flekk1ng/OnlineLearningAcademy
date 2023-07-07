package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.model.TeacherCourse;
import com.flekk.OnlineLearningAcademy.model.User;
import com.flekk.OnlineLearningAcademy.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final TeacherCourseService teacherCourseService;
    private final AuthenticationService authenticationService;
    private final EnrollLessonService enrollLessonService;

    @Autowired
    public LessonController(LessonService lessonService, TeacherCourseService teacherCourseService,
                            AuthenticationService authenticationService, EnrollLessonService enrollLessonService) {
        this.lessonService = lessonService;
        this.teacherCourseService = teacherCourseService;
        this.authenticationService = authenticationService;
        this.enrollLessonService = enrollLessonService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('lessons:edit')")
    public String createLesson(@Valid @ModelAttribute LessonDto lessonDto) {
        lessonService.createLesson(lessonDto);

        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto teacher = currentUser.get();
            Long teacherId = teacher.getId();
            boolean isTeacherOnCourse = teacherCourseService.isTeacherOnCourse(teacherId, lessonDto.getCourseId());
            if (!isTeacherOnCourse) {
                teacherCourseService.addTeacherToCourse(teacherId, lessonDto.getCourseId());
            }
        }

        return "redirect:/course/edit/" + lessonDto.getCourseId();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('lessons:edit')")
    public String deleteLesson(@PathVariable Long id) {
        Long courseId = lessonService.findById(id).getCourseId();
        lessonService.deleteById(id);
        return "redirect:/course/edit/" + courseId;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('lessons:edit')")
    public String updateLesson(@ModelAttribute LessonDto lessonDto) {
        lessonService.updateLesson(lessonDto);
        return "redirect:/course/edit/" + lessonDto.getCourseId();
    }

    @PostMapping("/enroll/{lessonId}")
    @PreAuthorize("hasAuthority('lessons:enroll')")
    public String enrollLesson(@PathVariable Long lessonId) {
        Optional<UserDto> currentUser = authenticationService.getCurrentUser();
        if (currentUser.isPresent()) {
            UserDto student = currentUser.get();
            enrollLessonService.enrollStudentToLesson(student.getId(), lessonId);
        }
        LessonDto lesson = lessonService.findById(lessonId);
        return "redirect:/course/view/" + lesson.getCourseId();
    }
}
