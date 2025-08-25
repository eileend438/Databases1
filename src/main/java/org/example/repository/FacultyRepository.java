package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import org.example.domain.Faculty;



public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
    @Query("SELECT s.faculty FROM Student s WHERE s.id = :studentId")
    Optional<Faculty> findByStudentId(@Param("studentId") Long studentId);
}

