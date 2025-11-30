package com.ingsw.easytournament.model;

public class SessioneAggiornamentoIncontro {
    private static SessioneAggiornamentoIncontro instance;
    private Incontro incontro;

    private SessioneAggiornamentoIncontro() {}

    public static SessioneAggiornamentoIncontro getInstance() {
        if (instance == null) {
            instance = new SessioneAggiornamentoIncontro();
        }
        return instance;
    }

    public Incontro getIncontro() {
        return incontro;
    }
    public void setIncontro(Incontro incontro) {
        this.incontro = incontro;
    }

    public void pulisciSessione(){
        instance = null;
    }
}
