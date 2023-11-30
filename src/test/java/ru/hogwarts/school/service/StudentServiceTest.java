package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService studentService = new StudentService();

    @Test
    void createStudent_success() {
        Student expectedStudent = new Student(1L, "Ivan", 22);
        Student actualStudent = studentService.createStudent(expectedStudent);
        assertEquals(expectedStudent, actualStudent);
        assertTrue(studentService.getAll().contains(actualStudent));

    }

    @Test
    void findStudent_success() {
        Student expectedStudent = new Student(0L, "Ivan", 22);
        studentService.createStudent(expectedStudent);
        Student actualStudent = studentService.findStudent(1);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void editStudent_success() {
        Student expectedStudent = new Student(4L, "Ivan", 22);
        studentService.createStudent(expectedStudent);
        Student actualStudent = studentService.editStudent(expectedStudent);
        assertEquals(expectedStudent, actualStudent);
    }
    @Test
    void editStudent_shouldReturnNull() {
        Student student = new Student(4L, "Ivan", 22);
        Student actualStudent = studentService.editStudent(student);
        assertNull(actualStudent);
    }

    @Test
    void removeStudent_success() {
        Student expectedStudent = new Student(0L, "Ivan", 22);
        studentService.createStudent(expectedStudent);
        Student actualStudent = studentService.removeStudent(1);
        assertEquals(expectedStudent, actualStudent);
        assertFalse(studentService.getAll().contains(actualStudent));
    }

    @Test
    void getAll_success() {
        Student student1 = new Student(1L, "Ivan", 22);
        Student student2 = new Student(2L, "Petr", 19);
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(student1);
        expectedList.add(student2);
        studentService.createStudent(student1);
        studentService.createStudent(student2);
        List<Student> actualList = studentService.getAll().stream().toList();
        assertEquals(expectedList, actualList);
    }

    @Test
    void filterByAge_success() {
        Student student1 = new Student(1L, "Ivan", 22);
        Student student2 = new Student(2L, "Petr", 19);
        Student student3 = new Student(1L, "Stepan", 19);
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(student2);
        expectedList.add(student3);
        studentService.createStudent(student1);
        studentService.createStudent(student2);
        studentService.createStudent(student3);
        Collection<Student> actualList = studentService.filterByAge(19);
        assertEquals(expectedList, actualList);
    }
}