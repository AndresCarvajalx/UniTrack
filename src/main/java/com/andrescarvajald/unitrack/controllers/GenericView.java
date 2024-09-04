package com.andrescarvajald.unitrack.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GenericView implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(hBox.widthProperty());
        imageView.fitHeightProperty().bind(hBox.heightProperty());
    }


    @FXML
    private ImageView imageView;
    @FXML
    private HBox hBox;
}
