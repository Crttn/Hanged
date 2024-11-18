package es.crttn.dad.controllers;

import es.crttn.dad.HangedApp;
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
    private String hiddenWord;


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

        hiddenWord = "_".repeat(wordProgressProperty.get().toString().length());
        wordProgressProperty.set(hiddenWord);
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
            winAlert.setContentText("Palabra oculta: " + wordProperty.get());
            winAlert.show();

            wordProperty.set(HangedApp.getRootController().getWordsController().getRandomWord());
            hiddenWord();
        } else {
            //SI falla se termina el juego y empieza de nuevo

            wordProperty.set(HangedApp.getRootController().getWordsController().getRandomWord());
            hiddenWord();
        }
    }

    @FXML
    void onTryLetterAction(ActionEvent event) {

        // Validar entrada
        if (inputProperty.get() == null || inputProperty.get().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Entrada inválida");
            errorAlert.setHeaderText("Por favor, introduce una letra válida.");
            errorAlert.show();
            return;
        }

        char letra = inputProperty.get().toUpperCase().charAt(0);
        boolean acierto = false;

        StringBuilder hiddenWordBuilder = new StringBuilder(hiddenWord);

        // Verificar cada letra de la palabra
        for (int i = 0; i < wordProperty.get().length(); i++) {
            if (wordProperty.get().charAt(i) == letra) {
                hiddenWordBuilder.setCharAt(i, letra);
                acierto = true;
            }
        }

        if (acierto) {
            // Actualizar progreso
            hiddenWord = hiddenWordBuilder.toString();
            wordProgressProperty.set(hiddenWord);

            // Verificar si se ha completado la palabra
            if (hiddenWord.equals(wordProperty.get())) {
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("¡Has Ganado!");
                winAlert.setHeaderText("Felicidades, has adivinado la palabra.");
                winAlert.setContentText("La palabra era: " + wordProperty.get());
                winAlert.show();

                // Reiniciar el juego
                wordProperty.set(HangedApp.getRootController().getWordsController().getRandomWord());
                hiddenWord();
            }
        } else {
            // Restar una vida si no acertó
            lifesProperty.set(lifesProperty.get() + 1);
            updateImage();

            // Actualizar letras falladas
            failedLetterProperty.set(failedLetterProperty.get() + letra + " ");

            // Verificar si ha perdido
            if (lifesProperty.get() <= 0) {
                Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
                loseAlert.setTitle("Has Perdido");
                loseAlert.setHeaderText("Juego terminado");
                loseAlert.setContentText("La palabra era: " + wordProperty.get());
                loseAlert.show();

                // Reiniciar el juego
                wordProperty.set(HangedApp.getRootController().getWordsController().getRandomWord());
                hiddenWord();
                lifesProperty.set(maxLifeProperty.get());
                failedLetterProperty.set("");
                updateImage();
            }
        }

        // Limpiar el campo de texto después de cada intento
        inputProperty.set("");

    }
}
