package ru.gb;

import java.util.ArrayList;
import java.util.List;

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
}
