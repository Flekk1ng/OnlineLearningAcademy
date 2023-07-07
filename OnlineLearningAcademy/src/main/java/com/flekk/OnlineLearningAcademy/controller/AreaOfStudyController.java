package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.model.AreaOfStudy;
import com.flekk.OnlineLearningAcademy.model.Course;
import com.flekk.OnlineLearningAcademy.repository.AreaOfStudyRepository;
import com.flekk.OnlineLearningAcademy.repository.CourseRepository;
import com.flekk.OnlineLearningAcademy.service.AreaOfStudyService;
import com.flekk.OnlineLearningAcademy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/areaOfStudy")
public class AreaOfStudyController {
    private final AreaOfStudyService areaOfStudyService;
    private final CourseService courseService;

    @Autowired
    public AreaOfStudyController(AreaOfStudyService areaOfStudyService, CourseService courseService) {
        this.areaOfStudyService = areaOfStudyService;
        this.courseService = courseService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('areaOfStudy:read')")
    public String showListAreasOfStudy(Model model) {
        List<AreaOfStudyDto> areasOfStudy = areaOfStudyService.findAllAreasOfStudy();
        model.addAttribute("areasOfStudy", areasOfStudy);
        return "areas_of_study";
    }

    @GetMapping("/list/edit")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String showListEditAreasOfStudy(Model model) {
        List<AreaOfStudyDto> areasOfStudy = areaOfStudyService.findAllAreasOfStudy();
        model.addAttribute("areasOfStudy", areasOfStudy);
        return "areas_of_study_edit";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String createAreaOfStudy(@ModelAttribute AreaOfStudyDto areaOfStudyDto) {
        areaOfStudyService.createAreaOfStudy(areaOfStudyDto);
        return "redirect:/areaOfStudy/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String updateAreaOfStudy(@PathVariable Long id, Model model) {
        AreaOfStudyDto areaOfStudyDto = areaOfStudyService.findById(id);
        model.addAttribute("areaOfStudy", areaOfStudyDto);
        List<CourseDto> courses = courseService.findByAreaOfStudy(areaOfStudyDto);
        model.addAttribute("courses", courses);
        return "area_of_study_edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String updateAreaOfStudy(@ModelAttribute AreaOfStudyDto areaOfStudyDto) {
        areaOfStudyService.updateAreaOfStudy(areaOfStudyDto);
        return "redirect:/areaOfStudy/list/edit";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String deleteAreaOfStudy(@PathVariable Long id) {
        areaOfStudyService.deleteById(id);
        return "redirect:/areaOfStudy/list/edit";
    }
}
