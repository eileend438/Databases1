package org.example.domain;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    public Faculty() {}

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }

    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }


}
