package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        if (studentService.findStudent(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @GetMapping(params = "age")
    public Collection<Student> getStudentsByAge(@RequestParam(required = false) Integer age) {
        return studentService.filterByAge(age);
    }

    @GetMapping
    public Collection<Student> getStudentsAll() {
        return studentService.getAll();
    }

    @GetMapping(params = {"min", "max"})
    public Collection<Student> getStudentsBetweenAge(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@RequestParam(required = false) String name) {
        if (studentService.getStudentFaculty(name) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentFaculty(name));
    }

    @PostMapping
    public Student addStudent(@RequestBody StudentDTO student) {
        return studentService.createStudent(student.getName(), student.getAge());
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.findStudent(student.getId());
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.editStudent(student.getId(), student.getName(), student.getAge(), student.getFaculty()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }
}
