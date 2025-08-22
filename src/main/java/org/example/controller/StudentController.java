package org.example.controller;

import org.example.domain.Student;
import org.example.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.ok(service.create(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        try {
            return ResponseEntity.ok(service.update(id, student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/students/by-age")
    public List<Student> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        return service.getStudentsByAgeBetween(min, max);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByFaculty(@PathVariable Long id) {
        return service.getStudentsByFacultyId(id);
    }

    @GetMapping("/count")
    public long getTotalStudents() {
        return service.getTotalStudents();
    }

    @GetMapping("/average-age")
    public double getAverageAge() {
        return service.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return service.getLastFiveStudents();
    }


}
