package es.crttn.dad.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WordsController implements Initializable {

    @FXML
    private Button wdAddButton;

    @FXML
    private ListView<String> wdListView;

    @FXML
    private Button wdRmButton;

    private ObservableList<String> wordsList;
    private static final String jsonFilePath = "/json/words.json";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wordsList = FXCollections.observableArrayList();
        wdListView.setItems(wordsList);

        wdAddButton.setOnAction(e -> addWord());
        wdRmButton.setOnAction(e -> removeWord());
    }

    void loadFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            List<String> palabras = new Gson().fromJson(json, new TypeToken<List<String>>() {}.getType());
            wordsList.setAll(palabras); // Actualizar ObservableList con palabras cargadas
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo JSON: " + e.getMessage());
        }
    }

    private void saveToJson() {
        try {
            String json = new Gson().toJson(wordsList);
            Files.write(Paths.get(jsonFilePath), json.getBytes());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
    }

    private void addWord() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Añadir Palabra");
        dialog.setHeaderText("Añadir una nueva palabra");
        dialog.setContentText("Palabra:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(palabra -> {
            if (!palabra.isBlank() && !wordsList.contains(palabra)) {
                wordsList.add(palabra); // Añadir palabra a la ObservableList
                saveToJson(); // Guardar cambios en JSON
            } else {
                System.out.println("La palabra ya existe o es inválida.");
            }
        });
    }

    private void removeWord() {
        String palabraSeleccionada = wdListView.getSelectionModel().getSelectedItem();
        if (palabraSeleccionada != null) {
            wordsList.remove(palabraSeleccionada); // Quitar palabra de la ObservableList
            saveToJson(); // Guardar cambios en JSON
        } else {
            System.out.println("Seleccione una palabra para eliminar.");
        }
    }


}
