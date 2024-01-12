package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }
    @Test
    void addStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Ivan", 15);
        //Подготовка ожидаемого результата
        Student expectedStudent = new Student("Ivan", 15);
        //Начало теста
        Student actualStudent = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
        expectedStudent.setId(actualStudent.getId());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getStudentInfo_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Ivan", 15);
        //Подготовка ожидаемого результата
        Student expectedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
        long id = expectedStudent.getId();
        //Начало теста
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void editStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Ivan", 15);
        //Подготовка ожидаемого результата
        Student studentForUpdate = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
        long id = studentForUpdate.getId();
        Student expectedStudent = new Student("Petr", 22);
        expectedStudent.setId(id);
        //Начало теста
        this.restTemplate.put("http://localhost:" + port + "/hogwarts/student", expectedStudent, Student.class);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student/" + id, Student.class);
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void removeStudent_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Ivan", 15);
        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
        long id = postedStudent.getId();
        //Начало теста
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student/" + id, Student.class);
        assertNotNull(actualStudent);
        this.restTemplate.delete("http://localhost:" + port + "/hogwarts/student/" + id);
        actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student/" + id, Student.class);
        assertNull(actualStudent);
    }

//    @Test
//    void getStudentsAll_success() {
//        //Подготовка входных данных
//        Student studentForCreate = new Student("Ivan", 15);
//        Student studentForCreate2 = new Student("Petr", 22);
//        //Подготовка ожидаемого результата
//        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
//        Student postedStudent2 = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate2, Student.class);
//        Collection<Student> expectedCollection = new ArrayList<>();
//        expectedCollection.add(postedStudent);
//        expectedCollection.add(postedStudent2);
//        //Начало теста
//        List<Student> actualCollection = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student", List.class);
//        assertNotNull(actualCollection);
//        assertEquals(expectedCollection, actualCollection);
//    }

    @Test
    void getStudentsByAge_success() {
        //Подготовка входных данных
        //Подготовка ожидаемого результата
        //Начало теста
    }

    @Test
    void getStudentsBetweenAge_success() {
        //Подготовка входных данных
        //Подготовка ожидаемого результата
        //Начало теста
    }

    @Test
    void getStudentFaculty_success() {
        //Подготовка входных данных
        Student studentForCreate = new Student("Stepan", 15);
        Faculty expectedFaculty = new Faculty("Stone", "grey");
        //Подготовка ожидаемого результата
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/student", studentForCreate, Student.class);
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/hogwarts/faculty", expectedFaculty, Faculty.class);
        expectedFaculty.setId(postedFaculty.getId());
        postedStudent.setFaculty(postedFaculty);
        this.restTemplate.put("http://localhost:" + port + "/hogwarts/student", postedStudent, Student.class);
        String studentName = postedStudent.getName();
        //Начало теста
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/hogwarts/student/faculty?name="+studentName, Faculty.class);
        assertNotNull(actualFaculty);
        assertEquals(expectedFaculty, actualFaculty);
    }
}