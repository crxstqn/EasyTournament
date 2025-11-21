package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.LoginRegistrazioneModel;
import com.ingsw.easytournament.utils.SceneChanger;
import com.ingsw.easytournament.utils.SessioneUtente;
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
    private TextField textusername;

    @FXML
    private PasswordField textpassword;

    @FXML
    void accedi(ActionEvent event) {
        String username = textusername.getText().trim().toLowerCase();
        String password = textpassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alert("Attenzione! Inserisci sia username che password");
            return;
        }

        if (password.length()<6) {
            alert("La password deve contenere almeno 6 caratteri");
            return;
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$")) {
            alert("La password deve contenere almeno una lettera e un numero");
            return;
        }

        boolean utenteEsistente = LoginRegistrazioneModel.autenticazione(username, password);

        if (utenteEsistente) {
            int id = LoginRegistrazioneModel.getUtenteId(username);
            SessioneUtente.getInstance(id, username);

            SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/login_reg.css");
            //dobbiamo aggiornare il path del css della home quando esisterà
        }else {
            alert("Attenzione! Credenziali errate. Riprova.");
            textpassword.clear();
        }
    }

    void alert(String testo){
        label_errore.setText(testo);
    }

    @FXML
    void registrati(ActionEvent event) {
        try {
            SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/registrazione.fxml", "/com/ingsw/easytournament/css/login_reg.css");
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