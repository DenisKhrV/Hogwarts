package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(String name, String color) {
        logger.info("Was invoked method for create faculty");
        Faculty newFaculty = new Faculty();
        newFaculty.setName(name);
        newFaculty.setColor(color);
        return facultyRepository.save(newFaculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Long id, String name, String color) {
        logger.info("Was invoked method for edit faculty");
        Faculty facultyForEdit = findFaculty(id);
        facultyForEdit.setName(name);
        facultyForEdit.setColor(color);
        return facultyRepository.save(facultyForEdit);
    }

    public void removeFaculty(long id) {
        logger.info("Was invoked method for remove faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }
    public Collection<Faculty> filterByColor(String color) {
        logger.info("Was invoked method for filter faculties by color ");
        return getAll()
                .stream().filter(student -> student.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculty by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> getStudentsOfFaculty(String name) {
        logger.info("Was invoked method for get students of faculty");
        Faculty faculty = facultyRepository.findByNameIgnoreCase(name);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }

    public String findLongestNameOfFaculty() {
        logger.info("Was invoked method for get longest name of faculty");
        return facultyRepository
                .findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .get();
    }
}
