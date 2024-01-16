package ru.gb;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StudentRepository {
    private final List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
        students.add(new Student("Oleg", "SAU"));
        students.add(new Student("Natasha", "FEA"));
        students.add(new Student("Petya", "SAU"));
        students.add(new Student("Olga", "IT"));
        students.add(new Student("Katya", "SAU"));
        students.add(new Student("Ivan", "IT"));
        students.add(new Student("Aleksandr", "IT"));
        students.add(new Student("Mihail", "FEA"));
        students.add(new Student("Angelika", "IT"));
        students.add(new Student("Alevtina", "SAU"));
    }

    public List<Student> getAll() {
        return List.copyOf(students);
    }

    public Student getById(long id) {
        return students.stream()
                .filter(student -> Objects.equals(student.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getByName(String name) {
        return students.stream()
                .filter(student -> student.getName().contains(name))
                .toList();
    }

    public List<Student> getStudentsByGroup(String groupName) {
        return students.stream()
                .filter(student -> student.getGroupName().equals(groupName))
                .toList();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean deleteStudent(long id) {
        Student student = getById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }
}
