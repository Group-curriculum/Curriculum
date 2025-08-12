package com.example.curriculum.controller;

import com.example.curriculum.model.Curriculum;
import com.example.curriculum.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curriculums")
public class CurriculumController {

    private final CurriculumRepository curriculumRepository;

    @Autowired
    public CurriculumController(CurriculumRepository curriculumRepository) {
        this.curriculumRepository = curriculumRepository;
    }

    @GetMapping
    public List<Curriculum> getAllCurriculums() {
        return curriculumRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curriculum> getCurriculumById(@PathVariable Long id) {
        Optional<Curriculum> curriculum = curriculumRepository.findById(id);
        return curriculum.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Curriculum createCurriculum(@RequestBody Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curriculum> updateCurriculum(@PathVariable Long id, @RequestBody Curriculum details) {
        return curriculumRepository.findById(id).map(curriculum -> {
            curriculum.setAcademicYear(details.getAcademicYear());
            curriculum.setProgramme(details.getProgramme());
            Curriculum updated = curriculumRepository.save(curriculum);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCurriculum(@PathVariable Long id) {
        return curriculumRepository.findById(id).map(curriculum -> {
            curriculumRepository.delete(curriculum);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
