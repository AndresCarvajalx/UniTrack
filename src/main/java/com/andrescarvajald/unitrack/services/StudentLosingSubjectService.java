package com.andrescarvajald.unitrack.services;

import com.andrescarvajald.unitrack.api.API;
import com.andrescarvajald.unitrack.api.entities.StudentLosingSubjects;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class StudentLosingSubjectService {
    private final API<StudentLosingSubjects> api = new API<StudentLosingSubjects>();
    private final TypeReference<List<StudentLosingSubjects>> type = new TypeReference<>() {};

    public StudentLosingSubjectService() {}

    public List<StudentLosingSubjects> get() {
        return api.get("historial/perdiendo-materias/", type);

    }
    public List<StudentLosingSubjects> get(int cedula) {
        //TODO
        return api.get("historial/perdiendo-materias/"+cedula, type);
    }
}
