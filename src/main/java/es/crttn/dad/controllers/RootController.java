package es.crttn.dad.controllers;

import javafx.application.Application;
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
    private TabPane gamePane;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane scorePane;

    @FXML
    private Tab wordsPane;

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

        Image image1 = new Image(getClass().getResourceAsStream("/images/1.png"));
        imageView.setImage(image1);
    }

    public AnchorPane getRoot() {
        return root;
    }
}
