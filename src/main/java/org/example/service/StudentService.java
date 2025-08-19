package org.example.service;

import org.example.domain.Student;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setAge(updatedStudent.getAge());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
