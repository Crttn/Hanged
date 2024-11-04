package es.crttn.dad;

import es.crttn.dad.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HangedApp extends Application {

    private final RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hanged Game");
        primaryStage.setScene(new Scene(rootController.getRoot(), 800, 600));
        primaryStage.show();
    }
}
