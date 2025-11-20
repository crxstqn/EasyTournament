package com.ingsw.easytournament.dao;

public interface utenteDAO {
    public boolean autenticazione(String username, String password);
    public boolean usernameEsistente(String username);
    public boolean registrazioneUtente(String nome, String username, String password);
    public int getUtenteId(String username);
}
