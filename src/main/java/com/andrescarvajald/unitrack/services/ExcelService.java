package com.andrescarvajald.unitrack.services;

import javafx.concurrent.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class ExcelService {
    private Sheet sheet;
    private Workbook workbook = null;
    private File file = null;

    public ExcelService() {
    }

    public void loadFile(File f) {
        this.file = f;
        try {
            this.workbook = new XSSFWorkbook(this.file);
            this.sheet = workbook.getSheetAt(0);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadData() {
        //generateExcelFile(this.workbook);
        System.out.println(getCellData(0, 0));
    }

    public String getCellData(int rowIndex, int cellIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                try {
                    FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellValue = evaluator.evaluate(cell);
                    switch (cellValue.getCellType()) {
                        case STRING:
                            return cellValue.getStringValue();
                        case NUMERIC:
                            return String.valueOf(cellValue.getNumberValue());
                        case BOOLEAN:
                            return String.valueOf(cellValue.getBooleanValue());
                        default:
                            return "";
                    }
                } catch (Exception e) {
                    return "Error evaluando fórmula: " + e.getMessage();
                }

            case BLANK:
                return "";

            default:
                return "Tipo de celda no soportado";
        }
    }

    public int getColumnCount() {
        int columnCount = 0;
        if (this.sheet != null) {
            Row row = this.sheet.getRow(0);
            if (row != null) {
                columnCount = row.getLastCellNum();
            }
        }
        return columnCount;
    }

    public int getRowCount() {
        int rowCount = 0;
        if (this.sheet != null) {
            rowCount = this.sheet.getLastRowNum();
        }
        return rowCount;
    }

    public void generateExcelFile(Workbook workbook) {
        String fileName = this.file.getName();
        fileName += "UniTrackReporte.xlsx";
        try {
            OutputStream output = new FileOutputStream(fileName);
            workbook.write(output);
        } catch (Exception e) {

        }
    }
}
