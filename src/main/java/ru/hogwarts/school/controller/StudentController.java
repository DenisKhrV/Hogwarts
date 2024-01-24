package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("/count")
    public int countStudents() {
        return studentService.countStudents();
    }

    @GetMapping("/average-age")
    public double averageAgeFromStudents() {
        return studentService.averageAge();
    }

    @GetMapping("/last-five")
    public Collection<Student> lastFiveStudents() {
        return studentService.lastFive();
    }

    @GetMapping("/name-begins-A")
    public ResponseEntity<List<String>> getNamesStudentsBeginsA() {
        return ResponseEntity.ok(studentService.getNamesStudentsBeginsA());
    }

    @GetMapping("/average-age-with-stream")
    public ResponseEntity<Double> getAverageAgeWithStream() {
        return ResponseEntity.ok(studentService.getAverageAgeWithStream());
    }

    @GetMapping("/students/print-parallel")
    public void printStudentsNameParallel() {
        studentService.printStudentsName();
    }
    @GetMapping("/students/print-synchronized")
    public void getStudentsNameSynchronized() {
        studentService.printStudentsNameSynchronized();
    }
}
