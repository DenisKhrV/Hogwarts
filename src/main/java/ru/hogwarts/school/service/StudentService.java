package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, Integer age) {
        logger.info("Was invoked method for create student");
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setAge(age);
        return studentRepository.save(newStudent);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Long id, String name, Integer age, Faculty faculty) {
        logger.info("Was invoked method for edit student");
        Student studentForEdit = findStudent(id);
        studentForEdit.setName(name);
        studentForEdit.setAge(age);
        studentForEdit.setFaculty(faculty);
        return studentRepository.save(studentForEdit);
    }

    public void removeStudent(long id) {
        logger.info("Was invoked method for remove student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Collection<Student> filterByAge(int age) {
        logger.info("Was invoked method for filter students by age");
        return getAll()
                .stream().filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(String name) {
        logger.info("Was invoked method for get student's faculty");
        Student student = studentRepository.findByName(name);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public int countStudents() {
        logger.info("Was invoked method for count students");
        return studentRepository.countStudents();
    }

    public double averageAge() {
        logger.info("Was invoked method for get average age students");
        return studentRepository.averageAgeFromStudents();
    }

    public Collection<Student> lastFive() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.lastFive();
    }

    public List<String> getNamesStudentsBeginsA() {
        logger.info("Was invoked method for get all names of all students whose name begins with the letter A");
        return studentRepository
                .findAll()
                .stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAgeWithStream() {
        logger.info("Was invoked method with stream for get average age students");
        return studentRepository
                .findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(-1);
    }
}
