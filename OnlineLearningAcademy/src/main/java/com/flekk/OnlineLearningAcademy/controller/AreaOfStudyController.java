package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import com.flekk.OnlineLearningAcademy.service.AreaOfStudyService;
import com.flekk.OnlineLearningAcademy.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String updateAreaOfStudy(@PathVariable Long id, Model model) {
        AreaOfStudyDto areaOfStudyDto = areaOfStudyService.findById(id);
        model.addAttribute("areaOfStudy", areaOfStudyDto);
        List<CourseDto> courses = courseService.findByAreaOfStudy(areaOfStudyDto);
        model.addAttribute("courses", courses);
        return "area_of_study_edit";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String createAreaOfStudy(@Valid @ModelAttribute AreaOfStudyDto areaOfStudyDto,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fields entered incorrectly");
            return "redirect:/areaOfStudy/list/edit";
        }

        try {
            areaOfStudyService.createAreaOfStudy(areaOfStudyDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/areaOfStudy/list/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String updateAreaOfStudy(@Valid @ModelAttribute AreaOfStudyDto areaOfStudyDto,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fields entered incorrectly");
            return "redirect:/areaOfStudy/edit/" + areaOfStudyDto.getId();
        }

        try {
            areaOfStudyService.updateAreaOfStudy(areaOfStudyDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/areaOfStudy/edit/" + areaOfStudyDto.getId();
        }

        return "redirect:/areaOfStudy/list/edit";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('areaOfStudy:edit')")
    public String deleteAreaOfStudy(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            areaOfStudyService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/areaOfStudy/list/edit";
    }

}
