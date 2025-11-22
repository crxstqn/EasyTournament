package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.SceneChanger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EasyTournamentApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(EasyTournamentApplication.class.getResource("/com/ingsw/easytournament/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/com/ingsw/easytournament/css/login_reg.css").toExternalForm());
        SceneChanger.getInstance().setStage(stage);

        stage.setResizable(false);
        stage.centerOnScreen();

        stage.setTitle("EasyTournament");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
