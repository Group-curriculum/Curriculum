package com.example.curriculum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String title;

    private Integer credits;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;
}
