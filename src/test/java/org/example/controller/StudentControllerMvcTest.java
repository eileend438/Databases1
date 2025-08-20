package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Student;
import org.example.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student("Harry", 17);
        Student saved = new Student("Harry", 17);

        Mockito.when(studentService.create(student)).thenReturn(saved);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student("Hermione", 18);

        Mockito.when(studentService.getById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hermione"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> list = List.of(
                new Student("Harry", 17),
                new Student("Ron", 16)
        );

        Mockito.when(studentService.getAll()).thenReturn(list);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student("Ron", 16);
        Student updated = new Student("Ronald", 16);

        Mockito.when(studentService.update(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ronald"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Mockito.doNothing().when(studentService).delete(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByAgeRange() throws Exception {
        List<Student> list = List.of(
                new Student("Luna", 15),
                new Student("Draco", 19)
        );

        Mockito.when(studentService.getStudentsByAgeBetween (14, 20)).thenReturn(list);

        mockMvc.perform(get("/students/students/by-age")
                        .param("min", "14")
                        .param("max", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetStudentNotFound() throws Exception {
        Mockito.when(studentService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
    }
}
