package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button buttonlogin;

    @FXML
    private Button buttonsignup;

    @FXML
    private Label label_errore;

    @FXML
    private TextField textmail;

    @FXML
    private PasswordField textpassword;

    @FXML
    void autenticaUtente(ActionEvent event) {
        SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/registrazione.fxml");
    }
}
