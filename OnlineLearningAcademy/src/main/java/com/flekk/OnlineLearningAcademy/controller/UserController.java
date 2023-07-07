package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.Dto.view.CourseStudentsDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.service.CourseService;
import com.flekk.OnlineLearningAcademy.service.StudentCourseService;
import com.flekk.OnlineLearningAcademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @Autowired
    public UserController(UserService userService, CourseService courseService, StudentCourseService studentCourseService) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('users:edit')")
    public String showListUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('users:edit')")
    public String updateUser(@ModelAttribute UserDto userDto) {
        userService.updateUser(userDto);
        return "redirect:/user/list";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('users:edit')")
    public String updateUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }

    @GetMapping("/students")
    @PreAuthorize("hasAuthority('students:read')")
    public String showListStudents(Model model) {
        List<CourseDto> courses = courseService.findAllCourses();
        List<CourseStudentsDto> courseStudentsList = new ArrayList<>();
        for (CourseDto course : courses) {
            List<UserDto> students = studentCourseService.findStudentsByCourseId(course.getId());
            CourseStudentsDto courseStudents = new CourseStudentsDto(course.getName(), students);
            courseStudentsList.add(courseStudents);
        }
        model.addAttribute("courseStudentsList", courseStudentsList);
        return "students";
    }

}
