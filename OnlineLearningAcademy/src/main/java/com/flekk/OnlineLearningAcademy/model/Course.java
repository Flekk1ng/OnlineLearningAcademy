package com.flekk.OnlineLearningAcademy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "area_of_study_id")
    private AreaOfStudy areaOfStudy;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<StudentCourse> studentCourses;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<TeacherCourse> teacherCourses;
}
