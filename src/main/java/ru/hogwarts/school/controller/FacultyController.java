package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        if (facultyService.findFaculty(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @GetMapping
    public ResponseEntity getFaculties(@RequestParam(required = false) String nameOrColor) {
        if (nameOrColor != null && !nameOrColor.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(nameOrColor));
        }
        return ResponseEntity.ok(facultyService.getAll());
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "color")
    public Collection<Faculty> getFacultyByColor(@RequestParam(required = false) String color) {
        return facultyService.filterByColor(color);
    }

    @GetMapping(params = "name")
    public Collection<Student> getStudentOfFaculty(@RequestParam(required = false) String name) {
        return facultyService.getStudentsOfFaculty(name);
    }
}
