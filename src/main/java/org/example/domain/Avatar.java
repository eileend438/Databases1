package org.example.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "avatars")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Avatar() {
    }

    public Avatar(String filePath, Student student) {
        this.filePath = filePath;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public Student getStudent() {
        return student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
