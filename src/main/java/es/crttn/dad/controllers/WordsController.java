package es.crttn.dad.controllers;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import es.crttn.dad.utils.StringUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class WordsController implements Initializable {

    private final ListProperty<String> wordsListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    @FXML
    private BorderPane root;
    @FXML
    private ListView<String> savedwordsListView;

    public WordsController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WordsView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/words.json")))) {

            JsonArray wordsArray = gson.fromJson(br, JsonArray.class);

            for (int i = 0; i < wordsArray.size(); i++) {
                String word = StringUtils.unaccent(wordsArray.get(i).getAsString().toUpperCase());
                wordsListProperty.add(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        savedwordsListView.itemsProperty().bindBidirectional(wordsListProperty);

    }


    public BorderPane getRoot() {
        return root;
    }

    public ListProperty<String> getWordsListPropertyProperty() {
        return wordsListProperty;
    }

    public String getRandomWord() {
        return wordsListProperty.get(new Random().nextInt(wordsListProperty.getSize()));
    }

    @FXML
    void onWordAddAction(ActionEvent event) {

        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Agregar Palabra");
        tid.setHeaderText("Aquí puedes agregar una palabra a la lista");
        tid.showAndWait().ifPresent(input -> {

            if (!input.isEmpty() && !input.isBlank()) {
                if (!wordsListProperty.contains(input.toUpperCase())) {
                    wordsListProperty.add(input.toUpperCase());
                }
            }
        });
    }

    @FXML
    void onDeleteWordAction(ActionEvent event) {
        wordsListProperty.remove(savedwordsListView.getSelectionModel().getSelectedItem());
    }
}

