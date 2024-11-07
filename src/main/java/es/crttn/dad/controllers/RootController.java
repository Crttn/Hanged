package es.crttn.dad.controllers;

import es.crttn.dad.HangedApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Tab gameTab;

    @FXML
    private Tab wordsTab;

    @FXML
    private Tab scoreTab;

    @FXML
    private TabPane tabPane;

    private final WordsController wordsController = new WordsController();
    private final GameController gameController = new GameController();
    private final ScoresController scoresController = new ScoresController();

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


        gameTab.setContent(gameController.getRoot());
        wordsTab.setContent(wordsController.getRoot());
        scoreTab.setContent(scoresController.getRoot());
        gameController.wordPropertyProperty().set(getWordsController().getRandomWord());

        gameController.hiddenWord();
    }

    public BorderPane getRoot() {
        return root;
    }

    public WordsController getWordsController() {
        return wordsController;
    }

    public GameController getGameController() {
        return gameController;
    }
}

