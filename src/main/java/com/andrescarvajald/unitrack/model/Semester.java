package com.andrescarvajald.unitrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Semester {
    private int id;
    @JsonProperty("estudiante")
    private Student student;
    private int nivel;
    private String periodo;
    private Double promedioPeriodo;
    private Double promedioFinal;
    private int cantidadAsignaturas;
    private int cantidadCreditos;

    public Semester() {}

    public Semester(int id, Student student, int nivel, String periodo, Double promedioPeriodo, Double promedioFinal, int cantidadAsignaturas, int cantidadCreditos) {
        this.id = id;
        this.student = student;
        this.nivel = nivel;
        this.periodo = periodo;
        this.promedioPeriodo = promedioPeriodo;
        this.promedioFinal = promedioFinal;
        this.cantidadAsignaturas = cantidadAsignaturas;
        this.cantidadCreditos = cantidadCreditos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getPromedioPeriodo() {
        return promedioPeriodo;
    }

    public void setPromedioPeriodo(Double promedioPeriodo) {
        this.promedioPeriodo = promedioPeriodo;
    }

    public Double getPromedioFinal() {
        return promedioFinal;
    }

    public void setPromedioFinal(Double promedioFinal) {
        this.promedioFinal = promedioFinal;
    }

    public int getCantidadAsignaturas() {
        return cantidadAsignaturas;
    }

    public void setCantidadAsignaturas(int cantidadAsignaturas) {
        this.cantidadAsignaturas = cantidadAsignaturas;
    }

    public int getCantidadCreditos() {
        return cantidadCreditos;
    }

    public void setCantidadCreditos(int cantidadCreditos) {
        this.cantidadCreditos = cantidadCreditos;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return this.student;
    }
}

