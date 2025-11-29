package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.HomeModel;
import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Date;

public class CardTorneoController {

    @FXML
    private Button button_elimina;

    @FXML
    private Button button_modifica;

    @FXML
    private Label label_data;

    @FXML
    private Label label_nome_torneo;

    private Torneo torneo;

    public void initialize(){
        button_elimina.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent::consume);
        button_modifica.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent::consume);
    }

    @FXML
    void eliminaTorneo(ActionEvent event) {
//        Task<Boolean> taskEliminazioneTorneo = new Task<>() {
//            @Override
//            protected Boolean call() throws Exception {
//                return HomeModel.eliminaTorneo(idTorneo);
//            }
//        };
//
//        taskEliminazioneTorneo.setOnSucceeded(e -> {
//        })
//
//
    }

    @FXML
    void visualizzaTorneo(MouseEvent event) {
        String fxmlDestinazione = "";
        String cssDestinazione = "/com/ingsw/easytournament/css/visualizzazione_torneo.css";

        switch (this.torneo.getIdModalità()){
            case 0: fxmlDestinazione = "/com/ingsw/easytournament/fxml/visualizza_torneo/girone_all_italiana.fxml"; break;
            case 1: fxmlDestinazione = "/com/ingsw/easytournament/fxml/visualizza_torneo/eliminazione_diretta.fxml"; break;
            case 2: fxmlDestinazione = "/com/ingsw/easytournament/fxml/visualizza_torneo/gironi_playoff.fxml"; break;
        }

        SessioneCreazioneTorneo.getInstance().eliminaTorneo();
        SessioneCreazioneTorneo.getInstance().setBozzaTorneo(this.torneo);

        SceneChanger.getInstance().changeScene(fxmlDestinazione,cssDestinazione,true);
    }

    @FXML
    void modificaTorneo(ActionEvent event) {
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
        label_nome_torneo.setText(torneo.getNome());
        label_data.setText(torneo.getData().toString());
    }
}
