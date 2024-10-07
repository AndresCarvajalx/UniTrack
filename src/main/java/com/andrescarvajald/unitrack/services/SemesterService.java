package com.andrescarvajald.unitrack.services;

import com.andrescarvajald.unitrack.api.API;
import com.andrescarvajald.unitrack.model.Historial;
import com.andrescarvajald.unitrack.model.Semester;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class SemesterService {
    private final API<Semester> api = new API<>();
    private final TypeReference<List<Semester>> typeRef = new TypeReference<List<Semester>>() {};
    private final String path = "semestre/";

    public List<Semester> get() {
        return api.get(path, typeRef);
    }

    public void update(Integer id, Semester s) {
        api.update(path + id, s);
    }

    public void add(Semester s) {
        api.add(path, s);
    }
}
