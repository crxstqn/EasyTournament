package com.ingsw.easytournament.model;

public class SessioneTorneo {
    private Torneo bozzaTorneo;
    private static SessioneTorneo instance;

    private SessioneTorneo() {};

    public static SessioneTorneo getInstance() {
        if (instance == null) {
            instance = new SessioneTorneo();
        }
        return instance;
    }

    public Torneo getBozzaTorneo() {
        return bozzaTorneo;
    }

    public void setBozzaTorneo(Torneo bozzaTorneo) {
        this.bozzaTorneo = bozzaTorneo;
    }

    public void eliminaTorneo() {
        instance = null;
    }
}
