package com.andrescarvajald.unitrack.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentLosingSubjects {
    private @JsonProperty("estudianteId") int studentId;
    private int cedula;
    private @JsonProperty("estudianteNombre") String studentName;
    private @JsonProperty("estudianteApellido") String studentLastName;
    private @JsonProperty("semestreNivel") int semester;
    private @JsonProperty("materiaNombre") String subjectName;
    private String jornada;
    private String estado;
    private @JsonProperty("vecesPerdida") Long losingTimes;

    public StudentLosingSubjects() {}

    public StudentLosingSubjects(
            int studentID,
            int cedula,
            String jornada,
            String estado,
            String studentName,
            String studentLastName,
            int semester,
            String subjectName,
            Long losingTimes
    ) {
        this.studentId = studentID;
        // TODO CHANGE CEDULA TYPE TO LONG
        this.cedula = cedula;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.semester = semester;
        this.subjectName = subjectName;
        this.losingTimes = losingTimes;
        this.estado = estado;
        this.jornada = jornada;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getLosingTimes() {
        return losingTimes;
    }

    public void setLosingTimes(Long losingTimes) {
        this.losingTimes = losingTimes;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
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
