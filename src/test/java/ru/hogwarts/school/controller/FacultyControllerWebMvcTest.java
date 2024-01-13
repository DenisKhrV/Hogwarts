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

        String name2 = "Gryffindor";
        String color2 = "red";
        long id2 = 2L;
        Faculty faculty2 = new Faculty(name, color);
        faculty2.setId(id);

        Collection<Faculty> collection = new ArrayList<>(List.of(faculty, faculty2));

        when(facultyService.getAll()).thenReturn(collection);


        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty"))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(faculty.getColor()))
                .andReturn();

    }

    @Test
    void getFacultyByColor_success() {
    }

    @Test
    void getStudentsOfFaculty_success() {
    }
}