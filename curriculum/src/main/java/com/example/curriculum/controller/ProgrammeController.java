package com.example.curriculum.controller;

import com.example.curriculum.model.Programme;
import com.example.curriculum.repository.ProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programmes")
public class ProgrammeController {

    private final ProgrammeRepository programmeRepository;

    @Autowired
    public ProgrammeController(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    @GetMapping
    public List<Programme> getAllProgrammes() {
        return programmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programme> getProgrammeById(@PathVariable Long id) {
        Optional<Programme> programme = programmeRepository.findById(id);
        return programme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Programme createProgramme(@RequestBody Programme programme) {
        return programmeRepository.save(programme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Programme> updateProgramme(@PathVariable Long id, @RequestBody Programme details) {
        return programmeRepository.findById(id).map(programme -> {
            programme.setName(details.getName());
            programme.setDepartment(details.getDepartment());
            Programme updated = programmeRepository.save(programme);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProgramme(@PathVariable Long id) {
        return programmeRepository.findById(id).map(programme -> {
            programmeRepository.delete(programme);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
