package com.andrescarvajald.unitrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject {
    private Integer id;
    @JsonProperty("nombre")
    private String name;

    public Subject() {}

    public Subject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
