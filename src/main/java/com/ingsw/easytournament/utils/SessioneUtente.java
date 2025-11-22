package com.ingsw.easytournament.utils;

public class SessioneUtente {

    private static SessioneUtente instance;

    private int userId;
    private String username;
    private String nome;

    private SessioneUtente() {
    }

    public static SessioneUtente getInstance() {
        if(instance == null) {
            instance = new SessioneUtente();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        this.nome = DatabaseConnessione.getInstance().getUtenteDAO().getUtenteNome(userId);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getNome(){
        return nome;
    }
}
