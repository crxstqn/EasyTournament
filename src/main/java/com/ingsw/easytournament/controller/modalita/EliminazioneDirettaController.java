package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class EliminazioneDirettaController {

    @FXML
    private Button button_avanti;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_titolo;

    @FXML
    private CheckBox scelta_finalina;

    private Torneo torneo;

    private Modalita bozzaModalita;

    public void initialize() {
        bozzaModalita = SessioneTorneo.getInstance().getBozzaTorneo().getModalita();
        scelta_finalina.setSelected(((EliminazioneDiretta) bozzaModalita).isFinalina());
    }
    @FXML
    void mostraRiepilogo(ActionEvent event) {
        boolean finalina = scelta_finalina.isSelected();

        //copiamo
        EliminazioneDiretta castModalita =  (EliminazioneDiretta) bozzaModalita;
        castModalita.setFinalina(finalina);
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_torneo.css", button_indietro.getScene());
    }

}
