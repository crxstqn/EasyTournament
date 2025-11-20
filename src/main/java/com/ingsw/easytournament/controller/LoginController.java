package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.LoginRegistrazioneModel;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    void accedi(ActionEvent event) {
        String username = textmail.getText().trim().toLowerCase();
        String password = textpassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alert("Attenzione! Inserisci sia username che password");
            return;
        }

        if (password.length()<6) {
            alert("La password deve contenere almeno 6 caratteri");
            return;
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).$")) {
            alert("La password deve contenere almeno una lettera e un numero");
            return;
        }

        try {
            boolean utenteEsistente = LoginRegistrazioneModel.autenticazione(username, password);

            if (utenteEsistente) {
                SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml");
            }else {
                alert("Attenzione! Credenziali errate. Riprova.");
                textpassword.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
            alert("Errore durante il login.");
        }
    }

    void alert(String testo){
        label_errore.setText(testo);
    }

    @FXML
    void registrati(ActionEvent event) {
        try {
            SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/registrazione.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void mouse_entered_login(MouseEvent event) {
        buttonlogin.setScaleX(1.05);
        buttonlogin.setScaleY(1.05);

    }

    @FXML
    void mouse_entered_registrati(MouseEvent event) {
        buttonsignup.setScaleX(1.05);
        buttonsignup.setScaleY(1.05);

    }

    @FXML
    void mouse_exit_login(MouseEvent event) {
        buttonlogin.setScaleX(1.0);
        buttonlogin.setScaleY(1.0);

    }

    @FXML
    void mouse_exited_registrati(MouseEvent event) {
        buttonsignup.setScaleX(1.0);
        buttonsignup.setScaleY(1.0);
    }
}