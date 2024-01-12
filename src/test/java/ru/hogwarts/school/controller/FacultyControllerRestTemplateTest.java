package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    void addFaculty_success() {
        //Подготовка входных данных
        Faculty facultyForCreate = new Faculty("Griff", "black");
        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty("Griff", "black");
        //Начало теста
        Faculty actualFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
        expectedFaculty.setId(actualFaculty.getId());
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    void getFacultyInfo_success() {
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
    void editFaculty_success() {
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
    void removeFaculty_success() {
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

//    @Test
//    void getFaculties_success() {
//        //Подготовка входных данных
//        Faculty facultyForCreate = new Faculty("Griff", "black");
//        Faculty facultyForCreate2 = new Faculty("Sliz", "White");
//        //Подготовка ожидаемого результата
//        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate, Faculty.class);
//        Faculty postedFaculty2 = this.restTemplate.postForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", facultyForCreate2, Faculty.class);
//        Collection<Faculty> expectedCollection = new ArrayList<>();
//        expectedCollection.add(postedFaculty);
//        expectedCollection.add(postedFaculty2);
//        //Начало теста
//        List <Faculty> actualCollection = this.restTemplate.getForObject("http://localhost:" + port + "/" + "/hogwarts/faculty", List.class);
//        assertNotNull(actualCollection);
//        assertEquals(expectedCollection, actualCollection);
//    }

    @Test
    void getFacultyByColor_success() {
    }

    @Test
    void getStudentsOfFaculty_success() {
    }
}