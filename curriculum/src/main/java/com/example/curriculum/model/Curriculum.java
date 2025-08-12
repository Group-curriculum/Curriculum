package com.example.curriculum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String academicYear;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;
}
