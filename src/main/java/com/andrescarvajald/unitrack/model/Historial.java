package com.andrescarvajald.unitrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Historial {
    private Integer id;
    @JsonProperty("estudiante")
    private Student student;
    @JsonProperty("semestre")
    private Semester semester;
    @JsonProperty("materia")
    private Subject subject;
    @JsonProperty("notaMateria")
    private Double subjectGrade;

    private String estadoMateria;

    public Historial() {}

    public Historial(Integer id, Student student, Semester semester, Subject subject, Double subjectGrade, String estadoMateria) {
        this.id = id;
        this.student = student;
        this.semester = semester;
        this.subject = subject;
        this.subjectGrade = subjectGrade;
        this.estadoMateria = estadoMateria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(Double subjectGrade) {
        this.subjectGrade = subjectGrade;
    }

    public String getEstadoMateria() {
        return estadoMateria;
    }

    public void setEstadoMateria(String estadoMateria) {
        this.estadoMateria = estadoMateria;
    }
}
