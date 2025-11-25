package com.ingsw.easytournament.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    public void changeSceneModalityMode(String fxmlPath, String cssPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.setTitle("Aggiungi Torneo");
            stage.setScene(scene);
            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object cambiaScena(String fxml, String css, Scene vecchiaScena) {
        try {
            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object controller = loader.getController();

            Stage stage = (Stage) vecchiaScena.getWindow();

            vecchiaScena.setRoot(root);

            vecchiaScena.getStylesheets().clear();
            vecchiaScena.getStylesheets().add(getClass().getResource(css).toExternalForm());

            stage.sizeToScene();
            stage.centerOnScreen();

            return controller;

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}