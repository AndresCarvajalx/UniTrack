package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.Main;
import com.andrescarvajald.unitrack.model.Semester;
import com.andrescarvajald.unitrack.model.Student;
import com.andrescarvajald.unitrack.model.Subject;
import com.andrescarvajald.unitrack.services.ExcelService;
import com.andrescarvajald.unitrack.services.StudentService;
import com.andrescarvajald.unitrack.services.SubjectService;
import com.andrescarvajald.unitrack.util.RomanNumeralParser;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OpenFileView implements Initializable {
    @FXML
    private Button openFileButton;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Button continueButton;


    private File file = null;
    private final ExcelService excelService = new ExcelService();
    private final SubjectService subjectService = new SubjectService();
    private final StudentService studentService = new StudentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileNameLabel.setText("Ningun archivo seleccionado aun");
        Main.stage.setTitle("UniTrack > Seleccion de Archivo");
    }

    private void openFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecion de archivo excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx", "*.xls"));
        this.file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            fileNameLabel.setText("Se leeran los datos de: " + file.getName());
            continueButton.setDisable(false);
            return;
        }
        fileNameLabel.setText("Error al abrir el archivo");
    }

    private void continueButtonAction() {
        excelService.loadFile(file);
        Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
        loadingAlert.setTitle("Subiendo los datos de " + file.getName() + " a la base de datos...");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        loadingAlert.setGraphic(progressIndicator);
        loadingAlert.getDialogPane().setContent(progressIndicator);
        loadingAlert.setHeaderText("Leyendo " + excelService.getRowCount() + " filas y " + excelService.getColumnCount() + " columnas");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    //TODO proceso de subida de datos del archivo
                    // Obtener las materias desde la fila 0, a partir de la columna 10
                    for (int j = 10; j < excelService.getColumnCount(); j++) {
                        String nombreMateria = excelService.getCellData(0, j);  // Fila 0, columnas desde la 10 en adelante
                        Subject materia = new Subject(-1, nombreMateria);
                        System.out.println("Materia creada: " + nombreMateria);
                        subjectService.add(materia);
                    }
                    for (int i = 1; i < excelService.getRowCount(); i++) {

                        String nombres = excelService.getCellData(i, 0);    // Columna 0: Nombres
                        String apellidos = excelService.getCellData(i, 1);  // Columna 1: Apellidos
                        long cedula = Long.parseLong(excelService.getCellData(i, 2));   // Columna 2: Cédula
                        String jornada = excelService.getCellData(i, 3);    // Columna 3: Jornada
                        int nivel = RomanNumeralParser.romanToInt(excelService.getCellData(i, 4));  // Columna 4: Nivel
                        String periodo = excelService.getCellData(i, 5);    // Columna 5: Período
                        //int cantidadAsignaturas = Integer.parseInt(excelService.getCellData(i, 6));  // Columna 6
                        //int cantidadCreditos = Integer.parseInt(excelService.getCellData(i, 7));     // Columna 7
                        //double promedioPeriodo = Double.parseDouble(excelService.getCellData(i, 8)); // Columna 8
                        //double promedioFinal = Double.parseDouble(excelService.getCellData(i, 9));   // Columna 9

                        // Crear el objeto Student
                        Student student = new Student(0, nombres, apellidos, cedula, jornada, "ACTIVO");

                        // Crear el objeto Semestre
                        Semester semestre = new Semester();
                        semestre.setStudent(student);
                        semestre.setNivel(nivel);
                        semestre.setPeriodo(periodo);
                        //semestre.setCantidadAsignaturas(cantidadAsignaturas);
                        //semestre.setCantidadCreditos(cantidadCreditos);
                        //semestre.setPromedioPeriodo(promedioPeriodo);
                        //semestre.setPromedioFinal(promedioFinal);
                        studentService.add(student);
                        // Procesar las materias y las notas
//                    List<Historial> historiales = new ArrayList<>();
//                    for (int j = 10; j < excelService.getColumnCount(); j++) {  // Columna 10 en adelante son las materias
//                        String nombreMateria = excelService.getCellData(0, j);  // Fila 0: nombres de materias
//                        double nota = Double.parseDouble(excelService.getCellData(i, j)); // Fila i: nota de la materia
//
//                        // Crear el objeto Subject (Materia)
//                        Subject materia = new Subject(nombreMateria);
//
//                        // Crear el objeto Historial
//                        Historial historial = new Historial();
//                        historial.setEstudiante(estudiante);
//                        historial.setSemestre(semestre);
//                        historial.setMateria(materia);
//                        historial.setNotaMateria(nota);
//                        historial.setEstadoMateria(nota >= 3.0 ? "APROBADA" : "PERDIDA");  // Lógica simple para definir estado
//
//                        historiales.add(historial);  // Agregar el historial a la lista
//                    }
//
//                    // Ahora, enviar estudiante, semestre e historiales al servidor
//                    // Aquí puedes usar tu servicio HTTP para enviar los datos
//                    studentService.save(estudiante);  // Guardar el estudiante en la BD
//                    semesterService.save(semestre);  // Guardar el semestre
//                    historialService.saveAll(historiales);  // Guardar el historial de materias del estudiante
                    }
                /*
                // Procesar los estudiantes (fila 1 en adelante) // TODO
                for (int i = 1; i < 10; i++) {  // Las primeras 10 filas contienen datos de estudiantes
                    String nombres = excelService.getCellData(i, 0);   // Columna 0: Nombres
                    String apellidos = excelService.getCellData(i, 1); // Columna 1: Apellidos
                    String jornada = excelService.getCellData(i, 2);   // Columna 2: Jornada
                    String estadoEstudiante = excelService.getCellData(i, 3); // Columna 3: Estado del estudiante
                    long cedula = Long.parseLong(excelService.getCellData(i, 4));    // Columna 4: Cédula

                    // Crear el objeto estudiante con los datos obtenidos
                    Student estudiante = new Student(nombres, apellidos, cedula, jornada, estadoEstudiante);

                    // Procesar el estudiante o guardarlo en la base de datos
                    // por ejemplo: estudianteService.save(estudiante);
                }
                excelService.uploadData();
                 */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                loadingAlert.close();
                loadingAlert.close();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Se subieron los datos con éxito");
                successAlert.showAndWait();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard-view.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Main.stage.setScene(scene);
                } catch (IOException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al cambiar de vista");
                        alert.setContentText("Message: " + e.getMessage());
                        alert.showAndWait();
                    });
                }
            }

            @Override
            protected void failed() {
                loadingAlert.close();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error al subir el archivo");
                errorAlert.show();
                System.out.println(this.getException().getMessage());
            }
        };
        loadingAlert.show();
        new Thread(task).start();
    }

    @FXML
    public void handleClicks(ActionEvent event) {
        if (event.getSource() == openFileButton) {
            openFileAction();
        } else if (event.getSource() == continueButton) {
            continueButtonAction();
        }
    }
}
