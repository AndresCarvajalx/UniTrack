package com.andrescarvajald.unitrack.services;

import com.andrescarvajald.unitrack.api.API;
import com.andrescarvajald.unitrack.model.Subject;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubjectService {
    private final API<Subject> api = new API<>();
    private final TypeReference<List<Subject>> typeRef = new TypeReference<List<Subject>>() {};
    private final String path = "materias/";

    public List<Subject> get() {
        return api.get(path, typeRef);
    }

    public Subject getByName(String name) {
        //URLEncoder.encode(name, StandardCharsets.UTF_8)
        return api.get(path + name, Subject.class);
    }

    public void update(Integer id, Subject subject) {
        api.update(path + id, subject);
    }

    public void add(Subject subject) {
        api.add(path, subject);
    }
}
