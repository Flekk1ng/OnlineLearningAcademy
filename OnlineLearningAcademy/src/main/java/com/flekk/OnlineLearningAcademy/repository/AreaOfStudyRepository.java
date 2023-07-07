package com.flekk.OnlineLearningAcademy.repository;

import com.flekk.OnlineLearningAcademy.model.AreaOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaOfStudyRepository extends JpaRepository<AreaOfStudy, Long> {
   Optional<AreaOfStudy> findByCoursesId(Long id);
}
