import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.example.domain.Student;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(17);

        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry", response.getBody().getName());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setName("Hermione");
        student.setAge(18);

        Student saved = restTemplate.postForObject("/students", student, Student.class);
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + saved.getId(), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hermione", response.getBody().getName());
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/students", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 0);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setName("Ron");
        student.setAge(16);
        Student saved = restTemplate.postForObject("/students", student, Student.class);

        saved.setName("Ronald");
        HttpEntity<Student> entity = new HttpEntity<>(saved);
        ResponseEntity<Student> response = restTemplate.exchange("/students/" + saved.getId(), HttpMethod.PUT, entity, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ronald", response.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("Neville");
        student.setAge(19);
        Student saved = restTemplate.postForObject("/students", student, Student.class);

        restTemplate.delete("/students/" + saved.getId());

        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + saved.getId(), Student.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStudentsByAgeRange() {
        restTemplate.postForObject("/students", new Student("Luna", 15), Student.class);
        restTemplate.postForObject("/students", new Student("Draco", 19), Student.class);

        ResponseEntity<Student[]> response = restTemplate.getForEntity("/students/students/by-age?min=14&max=20", Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
    }

    @Test
    public void testGetNonExistentStudent() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/999999", Student.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
