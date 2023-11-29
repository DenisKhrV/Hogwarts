package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long countId = 0;

    public Faculty createFaculty(Faculty faculty) {
        countId++;
        faculty.setId(countId);
        faculties.put(countId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty removeFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAll() {
        return faculties.values();
    }
    public Collection<Faculty> filterByColor(String color) {
        return getAll()
                .stream().filter(student -> student.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
