package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.model.StudentLosingSubjects;
import com.andrescarvajald.unitrack.services.StudentLosingSubjectService;
import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentLosingSubjectsView implements Initializable {
    @FXML
    private Button searchBtn;
    @FXML
    private TextField textField;
    @FXML
    private TableView<StudentLosingSubjects> tableView;
    @FXML
    private TableColumn<StudentLosingSubjects, Long> losingTimesColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, String> subjectColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, Integer> semesterColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, String> estadoColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, String> jornadaColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, Long> cedulaColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, String> lastnameColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, String> nameColumn;
    @FXML
    private TableColumn<StudentLosingSubjects, Integer> idColumn;

    private final StudentLosingSubjectService service = new StudentLosingSubjectService();
    private List<StudentLosingSubjects> defaultList;
    private ObservableList<StudentLosingSubjects> filteredList;

    @FXML
    public void handleClicks(ActionEvent event) {
        if(event.getSource() == searchBtn) {
            onFilter();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetButtonIcon.set(searchBtn, FontAwesomeSolid.SEARCH);
        this.filteredList = FXCollections.observableArrayList();
        this.defaultList = service.get();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("studentLastName"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        jornadaColumn.setCellValueFactory(new PropertyValueFactory<>("jornada"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        losingTimesColumn.setCellValueFactory(new PropertyValueFactory<>("losingTimes"));
        filteredList.addAll(defaultList);
        tableView.setItems(filteredList);
    }

    private void onFilter() {
        if(textField.getText().isEmpty()) {
            filteredList.clear();
            filteredList.addAll(defaultList);
            return;
        }
        Long cedula = Long.parseLong(textField.getText());
        List<StudentLosingSubjects> filtered = defaultList.stream().filter((e) -> e.getCedula() == cedula).toList();
        filteredList.clear();
        filteredList.addAll(filtered);
    }
}
