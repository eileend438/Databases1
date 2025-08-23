package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.domain.Faculty;
import org.example.domain.Student;
import org.example.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method to create faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getById(Long id) {
        logger.info("Was invoked method to get faculty by id: {}", id);
        return facultyRepository.findById(id);
    }

    public List<Faculty> getAll() {
        logger.info("Was invoked method to get all faculties");
        return facultyRepository.findAll();
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        logger.info("Was invoked method to update faculty with id: {}", id);
        return facultyRepository.findById(id)
                .map(faculty -> {
                    logger.debug("Updating faculty: {}", faculty);
                    faculty.setName(updatedFaculty.getName());
                    faculty.setColor(updatedFaculty.getColor());
                    return facultyRepository.save(faculty);
                })
                .orElseThrow(() -> {
                    logger.error("Faculty not found with id: {}", id);
                    return new IllegalArgumentException("Faculty not found: " + id);
                });
    }

    public List<Faculty> findByNameOrColor(String query) {
        logger.info("Was invoked method to find faculty by name or color: {}", query);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    public void delete(Long id) {
        logger.warn("Was invoked method to delete faculty with id: {}", id);
        facultyRepository.deleteById(id);
    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoked method to get students by faculty id: {}", facultyId);
        return facultyRepository.findById(facultyId)
                .map(faculty -> {
                    logger.debug("Found faculty: {}", faculty);
                    return faculty.getStudents();
                })
                .orElseThrow(() -> {
                    logger.error("Faculty not found with id: {}", facultyId);
                    return new EntityNotFoundException("Faculty not found");
                });
    }

    public Optional<Faculty> getFacultyByStudentId(Long studentId) {
        logger.info("Was invoked method to get faculty by student id: {}", studentId);
        return facultyRepository.findByStudentId(studentId);
    }
}
