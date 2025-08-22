package org.example.repository;

import org.example.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(int min, int max);

        @Query("SELECT COUNT(s) FROM Student s")
        long getTotalStudents();

        @Query("SELECT AVG(s.age) FROM Student s")
        double getAverageAge();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

}

