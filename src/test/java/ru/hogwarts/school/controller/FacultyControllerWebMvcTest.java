package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    //    @MockBean
    //    private FacultyRepository facultyRepository;
//    @MockBean
//    private StudentService studentService;
    @MockBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    @Test
    void addFaculty_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        Faculty facultyForCreate = new Faculty(name, color);

        String request = objectMapper.writeValueAsString(facultyForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Faculty facultyAfterCreate = new Faculty(name, color);
        facultyAfterCreate.setId(id);

        when(facultyService.createFaculty(name, color)).thenReturn(facultyAfterCreate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();
    }

    @Test
    void getFacultyInfo_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForCreate = new Faculty(name, color);
        facultyForCreate.setId(id);

        when(facultyService.findFaculty(id)).thenReturn(facultyForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();

    }

    @Test
    void editFaculty_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForUpdate = new Faculty(name, color);
        facultyForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(facultyForUpdate);

        //Подготовка ожидаемого результата
        when(facultyService.editFaculty(id, name, color)).thenReturn(facultyForUpdate);
        when(facultyService.findFaculty(id)).thenReturn(facultyForUpdate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(facultyForUpdate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(facultyForUpdate.getColor()))
                .andReturn();

    }

    @Test
    void removeFaculty_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForRemove = new Faculty(name, color);
        facultyForRemove.setId(id);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getFaculties_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty faculty= new Faculty(name, color);
        faculty.setId(id);

        String name2 = "Slytherin";
        String color2 = "green";
        long id2 = 2L;
        Faculty faculty2 = new Faculty(name2, color2);
        faculty2.setId(id2);

        Collection<Faculty> collection = new ArrayList<>(List.of(faculty, faculty2));

        when(facultyService.getAll()).thenReturn(collection);


        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(id2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].color").value(faculty2.getColor()))
                .andReturn();
    }

    @Test
    void getFacultyByColor_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty faculty= new Faculty(name, color);
        faculty.setId(id);

        String name3 = "Test";
        String color3 = "red";
        long id3 = 3L;
        Faculty faculty3= new Faculty(name3, color3);
        faculty3.setId(id3);

        Collection<Faculty> collection2 = new ArrayList<>(List.of(faculty, faculty3));

        when(facultyService.filterByColor("red")).thenReturn(collection2);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("color", "red"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(id3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value(faculty3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].color").value(faculty3.getColor()))
                .andReturn();
    }

    @Test
    void getFacultiesWithParam_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForCreate = new Faculty(name, color);
        facultyForCreate.setId(id);

        String nameOrColor = "Gryffindor";

        Collection<Faculty> collection = new ArrayList<>(List.of(facultyForCreate));

        when(facultyService.findByNameOrColor(nameOrColor)).thenReturn(collection);
        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("nameOrColor", "Gryffindor"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(facultyForCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].color").value(facultyForCreate.getColor()))
                .andReturn();

    }
    @Test
    void getStudentsOfFaculty_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        String studentName = "Harry";
        int studentAge = 15;
        Student student = new Student(studentName, studentAge);
        student.setId(1L);
        student.setFaculty(faculty);

        Collection<Student> students = new ArrayList<>(List.of(student));

        when(facultyService.getStudentsOfFaculty(name)).thenReturn(students);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students")
                        .param("facultyName", "Gryffindor"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(student.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].faculty.id").value(faculty.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].faculty.name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].faculty.color").value(faculty.getColor()))
                .andReturn();
    }
}