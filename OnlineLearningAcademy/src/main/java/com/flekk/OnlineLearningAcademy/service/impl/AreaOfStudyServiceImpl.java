package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.AreaOfStudyDto;
import com.flekk.OnlineLearningAcademy.model.AreaOfStudy;
import com.flekk.OnlineLearningAcademy.repository.AreaOfStudyRepository;
import com.flekk.OnlineLearningAcademy.service.AreaOfStudyService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaOfStudyServiceImpl implements AreaOfStudyService {
    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);

    private final AreaOfStudyRepository areaOfStudyRepository;

    @Autowired
    public AreaOfStudyServiceImpl(AreaOfStudyRepository areaOfStudyRepository) {
        this.areaOfStudyRepository = areaOfStudyRepository;
    }

    @Override
    public void createAreaOfStudy(AreaOfStudyDto areaOfStudyDto) {
        logger.info("Creating area of study {}", areaOfStudyDto.getName());
        try {
            AreaOfStudy areaOfStudy = new AreaOfStudy();
            areaOfStudy.setName(areaOfStudyDto.getName());
            areaOfStudy.setDescription(areaOfStudyDto.getDescription());
            areaOfStudyRepository.save(areaOfStudy);
            logger.info("Area of study {} created successfully", areaOfStudyDto.getName());
        } catch (Exception e) {
            logger.error("Error creating area of study {}: {}", areaOfStudyDto.getName(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateAreaOfStudy(AreaOfStudyDto areaOfStudyDto) {
        logger.info("Updating area of study {}", areaOfStudyDto.getId());
        try {
            AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(areaOfStudyDto.getId()).orElseThrow(()
                    -> new EntityNotFoundException("Area of study not found"));
            areaOfStudy.setName(areaOfStudyDto.getName());
            areaOfStudy.setDescription(areaOfStudyDto.getDescription());
            areaOfStudyRepository.save(areaOfStudy);
            logger.info("Area of study {} updated successfully", areaOfStudyDto.getId());
        } catch (Exception e) {
            logger.error("Error updating area of study {}: {}", areaOfStudyDto.getId(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting area of study {}", id);
        try {
            AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(id).orElseThrow(()
                    -> new EntityNotFoundException("Area of study not found"));
            areaOfStudyRepository.delete(areaOfStudy);
            logger.info("Area of study {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting area of study {}: {}", id, e.getMessage());
            throw e;
        }
    }


    @Override
    public List<AreaOfStudyDto> findAllAreasOfStudy() {
        List<AreaOfStudy> areasOfStudy = areaOfStudyRepository.findAll();
        return areasOfStudy.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AreaOfStudyDto findById(Long id) {
        AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Area of study not found"));
        return convertToDto(areaOfStudy);
    }

    @Override
    public AreaOfStudyDto findByCourseId(Long id) {
        AreaOfStudy areaOfStudy = areaOfStudyRepository.findByCoursesId(id).orElseThrow(()
                -> new EntityNotFoundException("Area of study not found"));
        return convertToDto(areaOfStudy);
    }

    private AreaOfStudyDto convertToDto(AreaOfStudy areaOfStudy) {
        AreaOfStudyDto areaOfStudyDto = new AreaOfStudyDto();
        areaOfStudyDto.setId(areaOfStudy.getId());
        areaOfStudyDto.setName(areaOfStudy.getName());
        areaOfStudyDto.setDescription(areaOfStudy.getDescription());
        return areaOfStudyDto;
    }
}
