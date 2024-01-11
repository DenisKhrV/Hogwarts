package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void getFacultyInfo() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Griff", "black");
        //Подготовка ожидаемого результата
        Faculty expectedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
        long id = expectedFaculty.getId();
        //Начало теста
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/" + "/hogwarts/faculty/" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    void getFaculties() {
    }

    @Test
    void addFaculty_success() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Griff", "black");
        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty("Griff", "black");
        expectedFaculty.setId(1L);
        //Начало теста
        Faculty actualFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    void editFaculty() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Griff", "black");
        //Подготовка ожидаемого результата
        Faculty facultyForUpdate = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
        long id = facultyForUpdate.getId();
        Faculty expectedFaculty = new Faculty("Sliz", "white");
        expectedFaculty.setId(id);
        //Начало теста
        this.restTemplate.put("http://localhost:" + port + "/" + "/hogwarts/faculty", expectedFaculty, Faculty.class);
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/" + "/hogwarts/faculty/" + id, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    void removeFaculty() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Griff", "black");
        //Подготовка ожидаемого результата
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
        long id = postedFaculty.getId();
        //Начало теста
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/" + "/hogwarts/faculty/" + id, Faculty.class);
        assertNotNull(actualFaculty);
        this.restTemplate.delete("http://localhost:" + port + "/" + "/hogwarts/faculty/" + id);
        actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/" + "/hogwarts/faculty/" + id, Faculty.class);
        assertNull(actualFaculty);
    }

    @Test
    void getFacultyByColor() {
    }

    @Test
    void getStudentsOfFaculty() {
    }
}