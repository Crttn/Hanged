package es.crttn.dad.controllers;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Tab wordsTab;

    @FXML
    private WordsController wordsController;

    private final ListProperty words = new SimpleListProperty(FXCollections.observableArrayList());

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (wordsController != null) {
            wordsController.loadFromJson(); // Carga las palabras al inicializar la aplicación
        } else {
            System.out.println("Error: WordsController no está inicializado.");
        }
    }

    public AnchorPane getRoot() {
        return root;
    }
}
