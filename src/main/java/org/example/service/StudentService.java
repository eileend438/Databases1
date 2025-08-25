package org.example.service;

import org.example.domain.Student;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        logger.info("Was invoked method create with student = {}", student);
        return studentRepository.save(student);
    }

    public Optional<Student> getById(Long id) {
        logger.info("Was invoked method getById with id = {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            logger.warn("No student found with id = {}", id);
        }
        return student;
    }

    public List<Student> getAll() {
        logger.info("Was invoked method getAll");
        return studentRepository.findAll();
    }

    public Student update(Long id, Student updatedStudent) {
        logger.info("Was invoked method update with id = {}, updatedStudent = {}", id, updatedStudent);
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setAge(updatedStudent.getAge());
                    logger.debug("Updating student with id = {}", id);
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> {
                    logger.error("Student not found: {}", id);
                    return new IllegalArgumentException("Student not found: " + id);
                });
    }

    public void delete(Long id) {
        logger.info("Was invoked method delete with id = {}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method getStudentsByAgeBetween with min = {}, max = {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoked method getStudentsByFacultyId with facultyId = {}", facultyId);
        return studentRepository.findAll().stream()
                .filter(s -> s.getFaculty() != null && facultyId.equals(s.getFaculty().getId()))
                .collect(Collectors.toList());
    }

    public long getTotalStudents() {
        logger.info("Was invoked method getTotalStudents");
        return studentRepository.getTotalStudents();
    }

    public double getAverageAge() {
        logger.info("Was invoked method getAverageAge");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method getLastFiveStudents");
        return studentRepository.getLastFiveStudents();
    }
    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("А"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    public double getAverageAgeViaStream() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public synchronized void printStudentName(String name) {
        System.out.println(name);
    }



}
