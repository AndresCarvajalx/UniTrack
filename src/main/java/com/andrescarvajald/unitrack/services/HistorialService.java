package com.andrescarvajald.unitrack.services;

import com.andrescarvajald.unitrack.api.API;
import com.fasterxml.jackson.core.type.TypeReference;
import com.andrescarvajald.unitrack.model.Historial;

import java.util.List;

public class HistorialService {
    private final API<Historial> api = new API<>();
    private final TypeReference<List<Historial>> typeRef = new TypeReference<List<Historial>>() {};
    private final String path = "historial/";

    public List<Historial> get() {
        return api.get(path, typeRef);
    }

    public void update(Integer id, Historial h) {
        api.update(path + id, h);
    }

    public void add(Historial h) {
        api.add(path, h);
    }
}
