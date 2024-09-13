package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.model.StudentLosingSubjects;
import com.andrescarvajald.unitrack.services.StudentLosingSubjectService;
import com.andrescarvajald.unitrack.util.Colors;
import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentLosingSubjectsView implements Initializable {

    private final StudentLosingSubjectService service = new StudentLosingSubjectService();
    private List<StudentLosingSubjects> defaultList;
    private ObservableList<StudentLosingSubjects> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtonIcons();
        initTable();
        initComboBox();
        textField.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                onFilter();
            }
        });
    }

    private void initTable() {
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

    private void initButtonIcons() {
        SetButtonIcon.set(searchBtn, FontAwesomeSolid.SEARCH);
        SetButtonIcon.set(generateReportBtn, FontAwesomeSolid.SAVE, Colors.PRIMARY);
    }

    private void initComboBox() {
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList();
        comboBoxItems.add("Todos");
        for (int i = 1; i <= 10; i++) {
            comboBoxItems.add(String.valueOf(i));
        }
        comboBox.getItems().addAll(comboBoxItems);

        comboBox.setOnAction(evt -> {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if(selectedItem.equals("Todos")) {
                    filteredList.clear();
                    filteredList.addAll(defaultList);
                    return;
                }
                List<StudentLosingSubjects> filtered = defaultList.stream().filter((e) -> e.getSemester() == Integer.parseInt(selectedItem)).toList();
                filteredList.clear();
                filteredList.addAll(filtered);
            }
        });
    }

    private void onFilter() {
        if (textField.getText().isEmpty()) {
            filteredList.clear();
            filteredList.addAll(defaultList);
            return;
        }
        Long cedula = Long.parseLong(textField.getText());
        List<StudentLosingSubjects> filtered = defaultList.stream().filter((e) -> e.getCedula().equals(cedula)).toList();
        filteredList.clear();
        filteredList.addAll(filtered);
    }

    @FXML
    public void handleClicks(ActionEvent event) {
        if (event.getSource() == searchBtn) {
            onFilter();
        }
    }

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button generateReportBtn;
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
}
