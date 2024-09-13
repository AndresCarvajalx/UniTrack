package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.model.Student;
import com.andrescarvajald.unitrack.model.StudentLosingSubjects;
import com.andrescarvajald.unitrack.services.StudentLosingSubjectService;
import com.andrescarvajald.unitrack.services.StudentService;
import com.andrescarvajald.unitrack.util.Colors;
import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentsView implements Initializable {
    private final StudentService service = new StudentService();
    private final List<Student> defaultList = service.get();
    private final ObservableList<Student> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetButtonIcon.set(editBtn, FontAwesomeSolid.EDIT, Colors.PRIMARY);
        initTable();
    }

    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        jornadaColumn.setCellValueFactory(new PropertyValueFactory<>("jornada"));
        observableList.addAll(defaultList);
        tableView.setItems(observableList);
    }

    private void editAction() {
        Student student = tableView.getFocusModel().getFocusedItem();
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Editar estado de " + student.getName());
        dialog.setHeaderText("Modificar el estado, estado actual: " + student.getEstado());

        ButtonType confirmarButtonType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmarButtonType, ButtonType.CANCEL);

        ChoiceBox<String> estadoChoiceBox = new ChoiceBox<>();
        estadoChoiceBox.getItems().addAll("ACTIVO", "GRADUADO", "INACTIVO", "PERDIO CALIDAD ESTUDIANTIL");
        estadoChoiceBox.setValue(student.getEstado());
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nuevo estado:"), 0, 0);
        grid.add(estadoChoiceBox, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmarButtonType) {
                return estadoChoiceBox.getValue();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEstado -> {
            if(student.getEstado().equals(newEstado)) {
                return;
            }
            student.setEstado(newEstado);
            try {
                service.update(student.getId(), student);
                observableList.set(observableList.indexOf(student), student);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
    }

    @FXML
    public void handleClicks(ActionEvent event) {
        if(event.getSource() == editBtn) {
            editAction();
        }
    }

    @FXML
    private Button editBtn;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> estadoColumn;
    @FXML
    private TableColumn<Student, String> jornadaColumn;
    @FXML
    private TableColumn<Student, Long> cedulaColumn;
    @FXML
    private TableColumn<Student, String> lastnameColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, Integer> idColumn;
}
