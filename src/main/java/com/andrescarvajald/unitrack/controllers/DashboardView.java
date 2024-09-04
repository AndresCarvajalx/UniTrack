package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.Main;
import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardView implements Initializable {
    @FXML
    private Button studentsButton;
    @FXML
    private Button subjectsButton;
    @FXML
    private Button studentsLosingSubjectsButton;
    @FXML
    private Button filterStudentButton;
    @FXML
    private BorderPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            changeCenterView("generic-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.setOnKeyPressed((key) -> {
            if(key.getCode() == KeyCode.ESCAPE) {
                try {
                    changeCenterView("generic-view");
                } catch (IOException _) {}
            }
        });
        Main.stage.setTitle("UniTrack Dashboard > ");
        initButtonsIcons();
    }

    private void initButtonsIcons() {
        String color = "#B5A160";
        SetButtonIcon.set(studentsButton, FontAwesomeSolid.USER, color);
        SetButtonIcon.set(subjectsButton, FontAwesomeSolid.BOOK, color);
        SetButtonIcon.set(studentsLosingSubjectsButton, FontAwesomeSolid.FROWN_OPEN, color);
        SetButtonIcon.set(filterStudentButton, FontAwesomeSolid.SEARCH, color);
    }

    private void changeCenterView(String fxml, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Main.stage.setTitle("UniTrack Dashboard > " + name);
        root.setCenter(fxmlLoader.load());
    }

    private void changeCenterView(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Main.stage.setTitle("UniTrack Dashboard > ");
        root.setCenter(fxmlLoader.load());
    }

    private void onClickStudentsButton() throws IOException {
        changeCenterView("students-view", "Estudiantes");
    }

    private void onClickSubjectsButton() throws IOException {
        changeCenterView("subjects-view", "Materias");
    }

    private void onClickStudentsLosingSubjectsButton() throws IOException {
        changeCenterView("students-losing-subjects-view", "Estudiantes Perdiendo Materias");
    }

    private void onClickFilterStudentButton() throws IOException {
        changeCenterView("filter-student-view", "Filtrar Estudiante");
    }

    @FXML
    public void handleClicks(ActionEvent event) throws IOException {
        Object source = event.getSource();
        if (source == studentsButton) {
            onClickStudentsButton();
        } else if (source == subjectsButton) {
            onClickSubjectsButton();
        } else if (source == studentsLosingSubjectsButton) {
            onClickStudentsLosingSubjectsButton();
        }  else if (source == filterStudentButton) {
            onClickFilterStudentButton();
        }
    }
}
