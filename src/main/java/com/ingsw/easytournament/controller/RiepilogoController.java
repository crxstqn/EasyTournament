package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.HomeModel;
import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RiepilogoController {

    @FXML
    private Button button_crea_torneo;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_descrizione;

    @FXML
    private Label label_titolo;

    @FXML
    private ListView<Squadra> list_view_squadre;

    private Torneo torneo;

    public void initialize(){
        this.torneo = SessioneCreazioneTorneo.getInstance().getBozzaTorneo();
        this.list_view_squadre.setEditable(false);
        label_descrizione.setText(this.torneo.getDescrizione());
        list_view_squadre.getItems().addAll(this.torneo.getSquadre());
    }

    @FXML
    void creaTorneo(ActionEvent event) {
        boolean outputUtente = mostraAlert("Sei sicuro di voler creare il torneo?", Alert.AlertType.CONFIRMATION);
        if (outputUtente) {
            Task<Boolean> taskCreazioneTorneo = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return HomeModel.creaTorneo(torneo);
                }
            };

            taskCreazioneTorneo.setOnSucceeded(e -> {
                boolean creato = taskCreazioneTorneo.getValue();
                if (creato) {
                    //chiudo finestra
                    Stage stage = ((Stage) button_crea_torneo.getScene().getWindow());
                    stage.close();
                } else {
                    mostraAlert("Errore durante la creazione del torneo!", Alert.AlertType.ERROR);
                }
            });

            //avvio torneo
            new Thread(taskCreazioneTorneo).start();
        }
    }


    @FXML
    void tornaIndietro(ActionEvent event) {
        int id_modalita = SessioneCreazioneTorneo.getInstance().getBozzaTorneo().getIdModalità();
        if (id_modalita == 0) {
            SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/modalita/girone_all_italiana.fxml", "/com/ingsw/easytournament/css/modalita/girone_all_italiana.css", button_indietro.getScene());
        }

        else if (id_modalita == 1) {
            SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/modalita/eliminazione_diretta.fxml", "/com/ingsw/easytournament/css/modalita/eliminazione_diretta.css", button_indietro.getScene());
        }

        else if (id_modalita == 2) {
            SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/modalita/gironi_playoff.fxml", "/com/ingsw/easytournament/css/modalita/gironi_playoff.css", button_indietro.getScene());
        }
    }

    private boolean mostraAlert(String testoErrore, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setTitle("Attenzione");
        alert.setGraphic(null);
        alert.setContentText(testoErrore);
        alert.showAndWait();
        if (tipo == Alert.AlertType.CONFIRMATION) {
            return alert.getResult() == ButtonType.OK;
        }
        return true;
    }

}




