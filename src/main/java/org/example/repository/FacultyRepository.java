package org.example.repository;

import org.example.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

}
