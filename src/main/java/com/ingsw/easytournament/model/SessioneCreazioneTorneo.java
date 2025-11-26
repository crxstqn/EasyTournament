package com.ingsw.easytournament.model;

public class SessioneCreazioneTorneo {
    private Torneo bozzaTorneo;
    private static SessioneCreazioneTorneo instance;

    private SessioneCreazioneTorneo() {};

    public static SessioneCreazioneTorneo getInstance() {
        if (instance == null) {
            instance = new SessioneCreazioneTorneo();
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
