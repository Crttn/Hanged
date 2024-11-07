package es.crttn.dad.controllers;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Label failedLettersLabel;

    @FXML
    private Label lifesLabel;

    @FXML
    private Label maxLifesLabel;

    @FXML
    private Label wordProgressLabel;

    @FXML
    private ImageView playerStageImage;

    @FXML
    private TextField wordGuesserTextField;

    @FXML
    private BorderPane root;

    private final StringProperty failedLetterProperty = new SimpleStringProperty();
    private final IntegerProperty lifesProperty = new SimpleIntegerProperty();
    private final IntegerProperty maxLifeProperty = new SimpleIntegerProperty(9);
    private final StringProperty wordProgressProperty = new SimpleStringProperty();
    private final StringProperty inputProperty = new SimpleStringProperty("");
    private final StringProperty wordProperty = new SimpleStringProperty();
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>(new Image("/images/1.png"));


    public GameController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        failedLettersLabel.textProperty().bind(failedLetterProperty);
        lifesLabel.textProperty().bindBidirectional(lifesProperty, new NumberStringConverter());
        maxLifesLabel.textProperty().bindBidirectional(maxLifeProperty, new NumberStringConverter());
        wordProgressLabel.textProperty().bind(wordProgressProperty);
        wordGuesserTextField.textProperty().bindBidirectional(inputProperty);
        playerStageImage.imageProperty().bind(imageProperty);

    }

    public void updateImage() {
        imageProperty.set(new Image("/images/" + lifesProperty.get() + ".png"));
    }

    public void hiddenWord() {
        wordProgressProperty.set(wordProperty.get());
        String palabra = wordProgressProperty.get().toString();

        String palabraConGuiones = "_".repeat(palabra.length());
        wordProgressProperty.set(palabraConGuiones);
    }


    public BorderPane getRoot() {
        return root;
    }

    public IntegerProperty getLifesProperty() {
        return lifesProperty;
    }

    public IntegerProperty maxLifePropertyProperty() {
        return maxLifeProperty;
    }

    public StringProperty inputPropertyProperty() {
        return inputProperty;
    }

    public StringProperty wordPropertyProperty() {
        return wordProperty;
    }

    @FXML
    void onSolveAction(ActionEvent event) {

        if (inputProperty.get().equalsIgnoreCase(wordProperty.get())){
            Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
            winAlert.setTitle("Has Ganado");
            winAlert.setHeaderText("FELICIDADES HAS GANADO");
            winAlert.show();
        }
    }

    @FXML
    void onTryLetterAction(ActionEvent event) {



    }
}
