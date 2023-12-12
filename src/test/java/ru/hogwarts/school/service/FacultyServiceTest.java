//package ru.hogwarts.school.service;
//
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Faculty;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FacultyServiceTest {
//    FacultyService facultyService = new FacultyService(facultyService);
//    @Test
//    void createFaculty_success() {
//        Faculty expectedFaculty = new Faculty(1L, "Ivan", "red");
//        Faculty actualFaculty = facultyService.createFaculty(expectedFaculty);
//        assertEquals(expectedFaculty, actualFaculty);
//        assertTrue(facultyService.getAll().contains(actualFaculty));
//    }
//
//    @Test
//    void findFaculty_success() {
//        Faculty expectedFaculty = new Faculty(0L, "Ivan", "red");
//        facultyService.createFaculty(expectedFaculty);
//        Faculty actualFaculty = facultyService.findFaculty(1);
//        assertEquals(expectedFaculty, actualFaculty);
//    }
//
//    @Test
//    void editFaculty_success() {
//        Faculty expectedFaculty = new Faculty(4L, "Ivan", "red");
//        facultyService.createFaculty(expectedFaculty);
//        Faculty actualFaculty = facultyService.editFaculty(expectedFaculty);
//        assertEquals(expectedFaculty, actualFaculty);
//    }
//    @Test
//    void editFaculty_shouldReturnNull() {
//        Faculty faculty = new Faculty(4L, "Ivan", "red");
//        Faculty actualFaculty = facultyService.editFaculty(faculty);
//        assertNull(actualFaculty);
//    }
//
//    @Test
//    void removeFaculty_success() {
//        Faculty expectedFaculty = new Faculty(0L, "Ivan", "red");
//        facultyService.createFaculty(expectedFaculty);
//        Faculty actualFaculty = facultyService.removeFaculty(1);
//        assertEquals(expectedFaculty, actualFaculty);
//        assertFalse(facultyService.getAll().contains(actualFaculty));
//    }
//
//    @Test
//    void getAll_success() {
//        Faculty faculty1 = new Faculty(1L, "Ivan", "red");
//        Faculty faculty2 = new Faculty(2L, "Petr", "blue");
//        List<Faculty> expectedList = new ArrayList<>();
//        expectedList.add(faculty1);
//        expectedList.add(faculty2);
//        facultyService.createFaculty(faculty1);
//        facultyService.createFaculty(faculty2);
//        List<Faculty> actualList = facultyService.getAll().stream().toList();
//        assertEquals(expectedList, actualList);
//    }
//
//    @Test
//    void filterByColor_success() {
//        Faculty faculty1 = new Faculty(1L, "Ivan", "red");
//        Faculty faculty2 = new Faculty(2L, "Petr", "blue");
//        Faculty faculty3 = new Faculty(1L, "Stepan", "blue");
//        List<Faculty> expectedList = new ArrayList<>();
//        expectedList.add(faculty2);
//        expectedList.add(faculty3);
//        facultyService.createFaculty(faculty1);
//        facultyService.createFaculty(faculty2);
//        facultyService.createFaculty(faculty3);
//        Collection<Faculty> actualList = facultyService.filterByColor("blue");
//        assertEquals(expectedList, actualList);
//    }
//}