package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;

import java.util.List;

public interface AreaOfStudyService {
    void createAreaOfStudy(AreaOfStudyDto areaOfStudyDto);
    void updateAreaOfStudy(AreaOfStudyDto areaOfStudyDto);
    void deleteById(Long id);
    List<AreaOfStudyDto> findAllAreasOfStudy();
    AreaOfStudyDto findById(Long id);
    AreaOfStudyDto findByCourseId(Long id);
}
