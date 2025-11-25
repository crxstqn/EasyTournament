package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.Torneo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class GironiPlayOffController implements ModalitaController{

    @FXML
    private Button button_avanti;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_titolo;

    @FXML
    private Spinner<?> numero_gironi;

    @FXML
    private Spinner<?> numero_squadre_girone;

    @FXML
    private Spinner<?> numero_vincitrici_girone;

    @FXML
    private CheckBox scelta_andata_ritorno;

    @FXML
    private CheckBox scelta_finalina;

    @FXML
    private Spinner<?> spinner_pareggio;

    @FXML
    private Spinner<?> spinner_sconfitta;

    @FXML
    private Spinner<?> spinner_vittoria;

    private Torneo torneo;

    @Override
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    @FXML
    void mostraRiepilogo(ActionEvent event) {

    }

    @FXML
    void tornaIndietro(ActionEvent event) {

    }

}
