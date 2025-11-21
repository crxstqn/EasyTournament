package com.ingsw.easytournament.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    private static Stage mainStage; // stage principale condiviso

    private static SceneChanger instance;

    private SceneChanger(){}

    public static SceneChanger getInstance() {
        if (instance == null) {
            instance = new SceneChanger();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        mainStage = stage;
    }

    public void changeScene(String fxmlPath, String cssPath, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = mainStage.getScene();
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            mainStage.setResizable(resizable);

            if (scene == null) {
                scene = new Scene(root);
                mainStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}