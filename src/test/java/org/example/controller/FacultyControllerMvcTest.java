package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Faculty;
import org.example.service.FacultyService;
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

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        Faculty saved = new Faculty("Gryffindor", "Red");

        Mockito.when(facultyService.create(faculty)).thenReturn(saved);

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty("Slytherin", "Green");

        Mockito.when(facultyService.getById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Slytherin"));
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        List<Faculty> list = List.of(
                new Faculty("Gryffindor", "Red"),
                new Faculty("Hufflepuff", "Yellow")
        );

        Mockito.when(facultyService.getAll()).thenReturn(list);

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty updated = new Faculty("Ravenclaw", "Blue");

        Mockito.when(facultyService.update(eq(1L), any(Faculty.class))).thenReturn(updated);

        mockMvc.perform(put("/faculties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ravenclaw"));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Mockito.doNothing().when(facultyService).delete(1L);

        mockMvc.perform(delete("/faculties/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByColor() throws Exception {
        List<Faculty> list = List.of(
                new Faculty("Gryffindor", "Red")
        );

        Mockito.when(facultyService.findByNameOrColor("Red")).thenReturn(list);

        mockMvc.perform(get("/faculties/search")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    public void testSearchByName() throws Exception {
        List<Faculty> list = List.of(
                new Faculty("Slytherin", "Green")
        );

        Mockito.when(facultyService.findByNameOrColor("Slytherin")).thenReturn(list);

        mockMvc.perform(get("/faculties/search")
                        .param("name", "Slytherin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Slytherin"))
                .andExpect(jsonPath("$[0].color").value("Green"));
    }

}
