package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;

public class LoginRegistrazioneModel {
    public static boolean autenticazione(String username, String password) {
        return DatabaseConnessione.getInstance().getUtenteDAO().autenticazione(username, password);
    }

    public static boolean usernameEsistente(String username) {
        return DatabaseConnessione.getInstance().getUtenteDAO().usernameEsistente(username);
    }

    public static boolean registrazioneUtente(String nome, String username, String password) {
        return DatabaseConnessione.getInstance().getUtenteDAO().registrazioneUtente(nome, username, password);
    }

    public static int getUtenteId(String username) {
        return DatabaseConnessione.getInstance().getUtenteDAO().getUtenteId(username);
    }
}
