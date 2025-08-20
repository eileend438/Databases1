package test.java;

import org.example.domain.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculties", faculty, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
    }

    @Test
    public void testGetFacultyById() {
        Faculty faculty = new Faculty();
        faculty.setName("Ravenclaw");
        faculty.setColor("Blue");

        Faculty saved = restTemplate.postForObject("/faculties", faculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + saved.getId(), Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ravenclaw", response.getBody().getName());
    }

    @Test
    public void testGetAllFaculties() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculties", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 0);
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Hufflepuff");
        faculty.setColor("Yellow");

        Faculty saved = restTemplate.postForObject("/faculties", faculty, Faculty.class);

        saved.setColor("Gold");
        HttpEntity<Faculty> entity = new HttpEntity<>(saved);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculties/" + saved.getId(), HttpMethod.PUT, entity, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gold", response.getBody().getColor());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Slytherin");
        faculty.setColor("Green");

        Faculty saved = restTemplate.postForObject("/faculties", faculty, Faculty.class);

        restTemplate.delete("/faculties/" + saved.getId());

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + saved.getId(), Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchFacultyByColorOrName() {
        restTemplate.postForObject("/faculties", new Faculty("Beauxbatons", "Blue"), Faculty.class);
        restTemplate.postForObject("/faculties", new Faculty("Durmstrang", "Dark"), Faculty.class);

        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculties/search?color=blue", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
    }

    @Test
    public void testGetNonExistentFaculty() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/999999", Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
