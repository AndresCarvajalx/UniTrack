module com.andrescarvajald.unitrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.andrescarvajald.unitrack to javafx.fxml;
    exports com.andrescarvajald.unitrack;

    opens com.andrescarvajald.unitrack.controllers to javafx.fxml;
    exports com.andrescarvajald.unitrack.controllers;

    exports com.andrescarvajald.unitrack.model;
    opens com.andrescarvajald.unitrack.model to com.fasterxml.jackson.databind;
    exports com.andrescarvajald.unitrack.services;
    opens com.andrescarvajald.unitrack.services to javafx.fxml;
}