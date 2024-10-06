package com.andrescarvajald.unitrack.services;

import com.andrescarvajald.unitrack.api.API;
import com.andrescarvajald.unitrack.model.Student;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class StudentService {
    private final API<Student> api = new API<>();
    private final TypeReference<List<Student>> type = new TypeReference<>() {};
    private final String path  = "estudiantes/";

    public StudentService() {}

    public List<Student> get() {
        return api.get(path, type);
    }

    public void update(Integer id, Student student) {
        api.update(path + id, student);
    }

    public void add(Student student) {
        api.add(path, student);
    }
}
