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

public class RegistrazioneController {

    @FXML
    private Button buttonlogin;

    @FXML
    private Button buttonsignup;

    @FXML
    private Label label_errore;

    @FXML
    private TextField textnome;

    @FXML
    private TextField textusername;

    @FXML
    private PasswordField textpassword;

    @FXML
    private PasswordField textpassword_conferma;

    @FXML
    void accedi(ActionEvent event) {
        SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/login.fxml", "/com/ingsw/easytournament/css/login_reg.css",false);
    }

    @FXML
    void registrati(ActionEvent event) {
        label_errore.setText("");

        String nome = textnome.getText().trim();
        String username = textusername.getText().trim().toLowerCase();
        String password = textpassword.getText();
        String rip_password = textpassword_conferma.getText();

        if (nome.isEmpty() || username.isEmpty() || password.isEmpty() || rip_password.isEmpty()) {
            alert("Attenzione! Compila tutti i campi");
            return;
        }

        if (!nome.matches("^[a-zA-ZàèìòùÀÈÌÒÙ0-9 ._'-]{2,50}$")){
            alert("Inserisci un nome valido");
            return;
        }

        if (LoginRegistrazioneModel.usernameEsistente(username)) {
            alert("Username già utilizzato, prova con un'altro o effettua il login");
            return;
        }

        if (password.length()<6 || rip_password.length()<6) {
            alert("La password deve contenere almeno 6 caratteri");
            return;
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$")) {
            alert("La password deve contenere almeno una lettera e un numero");
            return;
        }

        if (!password.equals(rip_password)){
            alert("Attenzione! Le Password non coincidono");
            return;
        }

        buttonsignup.setDisable(true);
        buttonlogin.setDisable(true);
        buttonsignup.setText("Registrazione in corso...");
        label_errore.setText("");

        Task<Boolean> registrazioneTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return LoginRegistrazioneModel.registrazioneUtente(nome, username, password);
            }
        };

        registrazioneTask.setOnSucceeded(e-> {
            boolean riuscito = registrazioneTask.getValue();

            buttonlogin.setDisable(false);
            buttonsignup.setDisable(false);
            buttonsignup.setText("Registrazione");

            if (riuscito) {
                int id = LoginRegistrazioneModel.getUtenteId(username);
                SessioneUtente.getInstance().setUserId(id);
                SessioneUtente.getInstance().setUsername(username);
                SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/home.css", true);
            }
        });

        registrazioneTask.setOnFailed(e -> {
            buttonlogin.setDisable(false);
            buttonsignup.setDisable(false);
            buttonsignup.setText("Registrazione");
            alert("Errore di connessione al Database!");
            registrazioneTask.getException().printStackTrace();
        });

        new Thread(registrazioneTask).start();
    }

    void alert(String testo){
        label_errore.setText(testo);
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
    void mouse_exit_registrati(MouseEvent event) {
        buttonsignup.setScaleX(1.0);
        buttonsignup.setScaleY(1.0);

    }
}
