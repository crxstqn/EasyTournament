package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.HomeModel;
import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SceneChanger;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.smartcardio.Card;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private Label benvenuto_label;

    @FXML
    private Button bottone_aggiungi;

    @FXML
    private Label easy_tournament_label;

    @FXML
    private FlowPane flow_pane;

    @FXML
    private HBox hbox_centrale;

    @FXML
    private HBox hbox_superiore;

    @FXML
    private Label i_miei_tornei_label;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private VBox vbox_centrale;



    public void initialize(){
        caricaTornei();
        benvenuto_label.setText("Benvenuto,\n" + SessioneUtente.getInstance().getNome() + "!");

    }

    public void caricaTornei() {
        Task<List<Torneo>> caricatoreTornei = new Task<>(){

            @Override
            protected List<Torneo> call() throws Exception {
                return HomeModel.getTornei();
            }
        };

        caricatoreTornei.setOnSucceeded(e -> {
            List<Torneo> tornei = caricatoreTornei.getValue();
            flow_pane.getChildren().clear();
            try {
                for (Torneo t : tornei) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ingsw/easytournament/fxml/card_torneo.fxml"));
                    Parent card = loader.load();

                    CardTorneoController cardController = loader.getController();
                    cardController.setTorneo(t);
                    cardController.setHomeController(this);

                    flow_pane.getChildren().add(card);
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        new Thread(caricatoreTornei).start();
    }

    @FXML
    void aggiungiTorneo(ActionEvent event) {
        SessioneCreazioneTorneo.getInstance().eliminaTorneo();
        SceneChanger.getInstance().createModalityStage("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml","/com/ingsw/easytournament/css/aggiungi_torneo.css");
        caricaTornei();
    }


}
