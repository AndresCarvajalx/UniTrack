package com.andrescarvajald.unitrack.controllers;

import com.andrescarvajald.unitrack.Main;
import com.andrescarvajald.unitrack.model.Historial;
import com.andrescarvajald.unitrack.model.Semester;
import com.andrescarvajald.unitrack.model.Student;
import com.andrescarvajald.unitrack.model.Subject;
import com.andrescarvajald.unitrack.services.*;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

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
    private final SemesterService semesterService = new SemesterService();
    private final HistorialService historialService = new HistorialService();

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
                    List<Subject> subjectList = new ArrayList<>(subjectService.get());

                    for (int i = 1; i < excelService.getRowCount(); i++) { // Bucle para cada estudiante
                        // Obtener datos del estudiante
                        String nombres = excelService.getCellData(i, 0);    // Columna 0: Nombres
                        String apellidos = excelService.getCellData(i, 1);  // Columna 1: Apellidos
                        long cedula = Long.parseLong(excelService.getCellData(i, 2));   // Columna 2: Cédula
                        String jornada = excelService.getCellData(i, 3);    // Columna 3: Jornada
                        int nivel = RomanNumeralParser.romanToInt(excelService.getCellData(i, 4));  // Columna 4: Nivel
                        String periodo = excelService.getCellData(i, 5);    // Columna 5: Período
                        int cantidadAsignaturas = (int) Double.parseDouble(excelService.getCellData(i, 6));  // Columna 6
                        int cantidadCreditos = (int) Double.parseDouble(excelService.getCellData(i, 7));     // Columna 7
                        String promedioProAux = excelService.getCellData(i, 8);
                        if(promedioProAux.isEmpty()) {
                            promedioProAux = "0.0";
                        }
                        double promedioPeriodo = Double.parseDouble(promedioProAux); // Columna 8
                        double promedioFinal = Double.parseDouble(excelService.getCellData(i, 9));   // Columna 9

                        // Crear el objeto Student
                        Student student = new Student(-1, nombres, apellidos, cedula, jornada, "ACTIVO");
                        studentService.add(student);
                        // Crear el objeto Semestre
                        Student student1 = studentService.getByCedula(student.getCedula()); // Guardar el estudiante en el servicio
                        Semester semestre = new Semester(-1, student1, nivel, periodo, promedioPeriodo, promedioFinal, cantidadAsignaturas, cantidadCreditos);
                        semesterService.add(semestre);

                        // Crear el objeto Historial
                        Historial historial = new Historial();
                        historial.setId(0);
                        historial.setStudent(student1);

                        List<Semester> semesterList = new ArrayList<>(semesterService.get());
                        Semester semester = semesterList.stream()
                                .filter(s -> s.getStudent().getCedula().equals(student1.getCedula()))
                                .findFirst()
                                .orElse(null);

                        historial.setSemester(semester);
                        // Bucle para leer las notas de las materias
                        for (int j = 10; j < excelService.getColumnCount(); j++) { // Empezar desde la columna 10
                            // Obtener el nombre de la materia desde la fila 0
                            String nombreMateria = excelService.getCellData(0, j); // Fila 0, columna j
                            String subjectData = excelService.getCellData(i, j); // Obtener la nota de la celda correspondiente
                            String estado = subjectData.split("\\(")[0].trim(); // Obtener la parte antes del paréntesis
                            System.out.println("ESTADO  " + estado);
                            String notaStr = subjectData.replaceAll("[^0-9.]", "").trim();
                            System.out.println("NOTA  " + notaStr);
                            // Obtener la materia desde el servicio
                            System.out.println(nombreMateria.replaceAll(" ", "-"));

                            Subject subject = subjectList.stream()
                                    .filter(s -> s.getName().equalsIgnoreCase(nombreMateria))
                                    .findFirst()
                                    .orElse(null);

                            // Si la materia existe, agregar la nota al historial
                            if (subject != null) {
                                historial.setSubject(subject);
                                if (estado.isEmpty() || notaStr.isBlank()) {
                                    estado = "No cursada";
                                }
                                if(subjectData.isEmpty()) {
                                    estado = "No cursada";
                                    notaStr = "0.0";
                                }
                                if(notaStr.isEmpty()) {
                                    notaStr = "0.0";
                                }
                                historial.setEstadoMateria(estado);
                                System.out.println("NOTA -> " + notaStr);
                                historial.setSubjectGrade(Double.parseDouble(notaStr)); // Asumiendo que tienes un método para agregar notas en el historial

                                System.out.println("Nota para " + nombreMateria + " de " + nombres + " " + apellidos + ": " + estado + "  " + notaStr);
                            } else {
                                System.out.println("Materia no encontrada: " + nombreMateria);
                            }
                        }
                        historialService.add(historial);
                    }
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
        Thread thread = new Thread(task);
        thread.start();
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

/*

for (int i = 1; i < excelService.getRowCount(); i++) {
                        for(int j = 0; j < excelService.getColumnCount(); j++) {
                            String nombres = excelService.getCellData(i, 0);    // Columna 0: Nombres
                            String apellidos = excelService.getCellData(i, 1);  // Columna 1: Apellidos
                            long cedula = Long.parseLong(excelService.getCellData(i, 2));   // Columna 2: Cédula
                            String jornada = excelService.getCellData(i, 3);    // Columna 3: Jornada
                            int nivel = RomanNumeralParser.romanToInt(excelService.getCellData(i, 4));  // Columna 4: Nivel
                            String periodo = excelService.getCellData(i, 5);    // Columna 5: Período
                            int cantidadAsignaturas = (int) Double.parseDouble(excelService.getCellData(i, 6));  // Columna 6
                            int cantidadCreditos = (int) Double.parseDouble(excelService.getCellData(i, 7));     // Columna 7
                            double promedioPeriodo = Double.parseDouble(excelService.getCellData(i, 8)); // Columna 8
                            double promedioFinal = Double.parseDouble(excelService.getCellData(i, 9));   // Columna 9

                            Student student = new Student(0, nombres, apellidos, cedula, jornada, "ACTIVO");

                            // Crear el objeto Semestre
                            Semester semestre = new Semester();
                            semestre.setStudent(student);
                            semestre.setNivel(nivel);
                            semestre.setPeriodo(periodo);
                            semestre.setCantidadAsignaturas(cantidadAsignaturas);
                            semestre.setCantidadCreditos(cantidadCreditos);
                            semestre.setPromedioPeriodo(promedioPeriodo);
                            semestre.setPromedioFinal(promedioFinal);

                            studentService.add(student);

                            Subject subject = subjectService.getByName(excelService.getCellData(0, 10));

                            Historial historial = new Historial(0, student, semestre, subject, 0.0, "");
                        }
                    }
 */