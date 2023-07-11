package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.LessonDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
    public String createLesson(@Valid @ModelAttribute LessonDto lessonDto, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fields entered incorrectly");
            return "redirect:/course/edit/" + lessonDto.getCourseId();
        }

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
        }


        try {
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
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/course/edit/" + lessonDto.getCourseId();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('lessons:edit')")
    public String deleteLesson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Long courseId = lessonService.findById(id).getCourseId();
        try {
            lessonService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/course/edit/" + courseId;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('lessons:edit')")
    public String updateLesson(@Valid @ModelAttribute LessonDto lessonDto, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fields entered incorrectly");
            return "redirect:/course/edit/" + lessonDto.getCourseId();
        }

        try {
            lessonService.updateLesson(lessonDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/course/edit/" + lessonDto.getCourseId();
    }

    @PostMapping("/enroll/{lessonId}")
    @PreAuthorize("hasAuthority('lessons:enroll')")
    public String enrollLesson(@PathVariable Long lessonId, RedirectAttributes redirectAttributes) {
        try {
            Optional<UserDto> currentUser = authenticationService.getCurrentUser();
            if (currentUser.isPresent()) {
                UserDto student = currentUser.get();
                enrollLessonService.enrollStudentToLesson(student.getId(), lessonId);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        LessonDto lesson = lessonService.findById(lessonId);
        return "redirect:/course/view/" + lesson.getCourseId();
    }

}
