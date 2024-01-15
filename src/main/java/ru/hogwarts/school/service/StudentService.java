package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, Integer age) {
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setAge(age);
        return studentRepository.save(newStudent);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Long id, String name, Integer age, Faculty faculty) {
        Student studentForEdit = findStudent(id);
        studentForEdit.setName(name);
        studentForEdit.setAge(age);
        studentForEdit.setFaculty(faculty);
        return studentRepository.save(studentForEdit);
    }

    public void removeStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Collection<Student> filterByAge(int age) {
        return getAll()
                .stream().filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(String name) {
       Student student = studentRepository.findByName(name);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public int countStudents() {
        return studentRepository.countStudents();
    }

    public double averageAge() {
        return studentRepository.averageAgeFromStudents();
    }

    public Collection<Student> lastFive() {
        return studentRepository.lastFive();
    }
}
