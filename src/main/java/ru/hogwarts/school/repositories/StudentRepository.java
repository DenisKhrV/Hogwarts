package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int from, int to);

    Student findByName(String name);

    @Query(value = "SELECT Count(*) FROM student", nativeQuery = true)
    int countStudents();

    @Query(value = "select AVG(age) from student", nativeQuery = true)
    double averageAgeFromStudents();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    Collection<Student> lastFive();
}
