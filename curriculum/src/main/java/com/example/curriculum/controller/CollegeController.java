package com.example.curriculum.controller;

import com.example.curriculum.model.College;
import com.example.curriculum.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    private final CollegeRepository collegeRepository;

    @Autowired
    public CollegeController(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @GetMapping
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id) {
        Optional<College> college = collegeRepository.findById(id);
        return college.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public College createCollege(@RequestBody College college) {
        return collegeRepository.save(college);
    }

    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id, @RequestBody College collegeDetails) {
        return collegeRepository.findById(id).map(college -> {
            college.setName(collegeDetails.getName());
            college.setLocation(collegeDetails.getLocation());
            College updated = collegeRepository.save(college);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCollege(@PathVariable Long id) {
        return collegeRepository.findById(id).map(college -> {
            collegeRepository.delete(college);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
