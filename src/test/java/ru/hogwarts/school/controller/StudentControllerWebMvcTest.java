package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void addStudent_success() throws Exception {
        //Подготовка входных данных
        String name = "Ivan";
        int age = 25;
        Student studentForCreate = new Student(name, age);

        String request = objectMapper.writeValueAsString(studentForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Student studentAfterCreate = new Student(name, age);
        studentAfterCreate.setId(id);

        when(studentService.createStudent(name, age)).thenReturn(studentAfterCreate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }


    @Test
    void getStudentInfo_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Ivan";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);

        when(studentService.findStudent(id)).thenReturn(studentForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    void editStudent_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Ivan";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);
        Faculty faculty = new Faculty("Gryffindor", "red");
        studentForCreate.setFaculty(faculty);

        String request = objectMapper.writeValueAsString(studentForCreate);

        when(studentService.editStudent(id, name, age, faculty)).thenReturn(studentForCreate);
        when(studentService.findStudent(id)).thenReturn(studentForCreate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.faculty").value(studentForCreate.getFaculty()))
                .andReturn();
    }

    @Test
    void removeStudent_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Ivan";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentFaculty_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Ivan";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);
        Faculty faculty = new Faculty("Gryffindor", "red");
        studentForCreate.setFaculty(faculty);

        when(studentService.getStudentFaculty(name)).thenReturn(faculty);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty")
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(faculty.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(faculty.getColor()))
                .andReturn();

    }

    @Test
    void getStudentsByAge_success() {
    }

    @Test
    void getStudentsAll_success() {
    }

    @Test
    void getStudentsBetweenAge_success() {
    }


}