package es.crttn.dad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.crttn.dad.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class HangedApp extends Application {

    private static final RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hanged Game");
        primaryStage.setScene(new Scene(rootController.getRoot(), 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

        //guardar listas en JSon
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //guardar wordList
        List<String> wordList = new ArrayList<>(rootController.getWordsController().getWordsListPropertyProperty().get());


        File wordFile = new File(getClass().getResource("/json/words.json").toURI());

        try (FileWriter fw = new FileWriter(wordFile, false)) {

            fw.write(gson.toJson(wordList));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //guardar scoreList
        List<String> scoreList = new ArrayList<>(rootController.getWordsController().getWordsListPropertyProperty().get());


        File scoreFile = new File(getClass().getResource("/json/words.json").toURI());

        try (FileWriter fw = new FileWriter(scoreFile, false)) {

            fw.write(gson.toJson(scoreList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RootController getRootController() {
        return rootController;
    }
}
