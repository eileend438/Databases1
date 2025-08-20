package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.domain.Faculty;
import org.example.domain.Student;
import org.example.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.example.domain.Student;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getById(Long id) {
        return facultyRepository.findById(id);
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        return facultyRepository.findById(id)
                .map(faculty -> {
                    faculty.setName(updatedFaculty.getName());
                    faculty.setColor(updatedFaculty.getColor());
                    return facultyRepository.save(faculty);
                })
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found: " + id));
    }
    public List<Faculty> findByNameOrColor(String query) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found"));
    }
    public Optional<Faculty> getFacultyByStudentId(Long studentId) {
        return facultyRepository.findByStudentId(studentId);
    }
}
