package com.flekk.OnlineLearningAcademy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic")
    private String topic;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}