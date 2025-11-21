package com.ingsw.easytournament.utils;

public class SessioneUtente {

    private static SessioneUtente instance;

    private int userId;
    private String username;

    private SessioneUtente(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static SessioneUtente getInstance(int userId, String username) {
        if(instance == null) {
            instance = new SessioneUtente(userId, username);
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
