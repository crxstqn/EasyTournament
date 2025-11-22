package com.ingsw.easytournament.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    private int idTorneo;

    @FXML
    void eliminaTorneo(ActionEvent event) {

    }

    @FXML
    void modificaTorneo(ActionEvent event) {

    }

    public void setParametri(String nome, LocalDate data, int id) {
        this.idTorneo = id;
        label_nome_torneo.setText(nome);
        label_data.setText(data.toString());
    }
}
