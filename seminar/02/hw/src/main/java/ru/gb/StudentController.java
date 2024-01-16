package ru.gb;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable long id) {
        return repository.getById(id);
    }

    @GetMapping("/student")
    public List<Student> getStudents() {
        return repository.getAll();
    }

    @GetMapping("/student/search")
    public List<Student> getStudentsByName(@RequestParam String name) {
        return repository.getByName(name);
    }

    @GetMapping("/group/{groupName}/student")
    public List<Student> getStudentsByGroup(@PathVariable String groupName) {
        return repository.getStudentsByGroup(groupName);
    }

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        repository.addStudent(student);
        return student;
    }

    @DeleteMapping("/student/{id}")
    public Boolean deleteStudent(@PathVariable long id) {
        return repository.deleteStudent(id);
    }
}
