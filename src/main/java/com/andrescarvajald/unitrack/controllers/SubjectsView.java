package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.model.Subject;
import com.andrescarvajald.unitrack.services.SubjectService;
import com.andrescarvajald.unitrack.util.Colors;
import com.andrescarvajald.unitrack.util.SetButtonIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectsView implements Initializable {

    private final SubjectService service = new SubjectService();
    private final ObservableList<Subject> observableList = FXCollections.observableArrayList(service.get());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(observableList);
        SetButtonIcon.set(editBtn, FontAwesomeSolid.EDIT, Colors.PRIMARY);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void editAction() {
        Subject subject = tableView.getFocusModel().getFocusedItem();
        Dialog<String> dialog = new Dialog<>();
        HBox hBox = new HBox();
        TextField textField = new TextField();
        textField.setPromptText("Nombre");
        textField.setText(subject.getName());
        dialog.setTitle("Editar Materia");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(textField);
        ButtonType confirmarButtonType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmarButtonType, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(hBox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmarButtonType) {
                return textField.getText();
            }
            return null;
        });
        dialog.showAndWait().ifPresent(newName -> {
            subject.setName(newName);
            try {
                service.update(subject.getId(), subject);
                observableList.set(observableList.indexOf(subject), subject);
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
        if (event.getSource() == editBtn) {
            editAction();
        }
    }

    @FXML
    private Button editBtn;
    @FXML
    private TableView<Subject> tableView;
    @FXML
    private TableColumn<Subject, Integer> idColumn;
    @FXML
    private TableColumn<Subject, String> nameColumn;
}
