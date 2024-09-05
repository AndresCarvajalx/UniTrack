package com.andrescarvajald.unitrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    private Integer id;
    @JsonProperty("nombres")
    private String name;
    @JsonProperty("apellidos")
    private String lastName;
    private Long cedula;
    private String jornada;
    private String estado;

    public Student() {}

    public Student(Integer id, String name, String lastName, Long cedula, String jornada, String estado) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cedula = cedula;
        this.jornada = jornada;
        this.estado = estado;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
