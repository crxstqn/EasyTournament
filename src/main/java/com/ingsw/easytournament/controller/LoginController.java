package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.LoginRegistrazioneModel;
import com.ingsw.easytournament.utils.SceneChanger;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.concurrent.Task;
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

        buttonlogin.setDisable(true);
        buttonsignup.setDisable(true);
        buttonlogin.setText("Accesso in corso...");
        label_errore.setText("");

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return LoginRegistrazioneModel.autenticazione(username, password);
            }
        };


        loginTask.setOnSucceeded(e -> {
            boolean utenteEsistente = loginTask.getValue();

            // Ripristiniamo il bottone
            buttonlogin.setDisable(false);
            buttonsignup.setDisable(false);
            buttonlogin.setText("Accedi");

            if (utenteEsistente) {
                int id = LoginRegistrazioneModel.getUtenteId(username);
                SessioneUtente.getInstance().setUserId(id);
                SessioneUtente.getInstance().setUsername(username);
                SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/login_reg.css", true);
            } else {
                alert("Attenzione! Credenziali errate.");
                textpassword.clear();
            }
        });

        loginTask.setOnFailed(e -> {
            buttonlogin.setDisable(false);
            buttonsignup.setDisable(false);
            buttonlogin.setText("Accedi");
            alert("Errore di connessione al Database!");
            loginTask.getException().printStackTrace();
        });

        new Thread(loginTask).start();
    }

    void alert(String testo){
        label_errore.setText(testo);
    }

    @FXML
    void registrati(ActionEvent event) {
        try {
            SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/registrazione.fxml", "/com/ingsw/easytournament/css/login_reg.css",false);
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