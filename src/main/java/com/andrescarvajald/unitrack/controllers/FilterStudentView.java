package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterStudentView  implements Initializable {
    @FXML
    private TextField textField;
    @FXML
    private Button searchBtn;
    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtonIcon();
    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if(event.getSource() == searchBtn) {
            filterStudent();
        }
    }

    private void filterStudent() {
        //TODO CREAR SERVICIO
    }

    private void initButtonIcon() {
        SetButtonIcon.set(searchBtn, FontAwesomeSolid.SEARCH);
    }
}
