package es.crttn.dad.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import es.crttn.dad.utils.StringUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScoresController implements Initializable{

    @FXML
    private BorderPane root;

    @FXML
    private ListView<String> summaryListView;


    private final ListProperty<String> gamesListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ScoresController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScoreView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/scores.json")))) {

            JsonArray wordsArray = gson.fromJson(br, JsonArray.class);

            for (int i = 0; i < wordsArray.size(); i++) {
                String word = wordsArray.get(i).getAsString();
                gamesListProperty.add(word);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        summaryListView.itemsProperty().bindBidirectional(gamesListProperty);

    }

    public BorderPane getRoot() {
        return root;
    }

}
