package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class RiepilogoController {

    @FXML
    private Button button_indietro;

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

}
