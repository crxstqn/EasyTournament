package com.ingsw.easytournament.model;

public class Incontro {
    private int id;
    private int idTorneo;
    private String squadra1;
    private String squadra2;
    private int punteggioSquadra1;
    private int punteggioSquadra2;
    private int gruppo;

    public Incontro(int idTorneo, String squadra1, String squadra2, int gruppo) {
        this.idTorneo = idTorneo;
        this.punteggioSquadra1 = 0;
        this.punteggioSquadra2 = 0;
        this.squadra2 = squadra2;
        this.squadra1 = squadra1;
        this.gruppo = gruppo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getPunteggioSquadra1() {
        return punteggioSquadra1;
    }

    public void setPunteggioSquadra1(int punteggioSquadra1) {
        this.punteggioSquadra1 = punteggioSquadra1;
    }

    public int getPunteggioSquadra2() {
        return punteggioSquadra2;
    }

    public void setPunteggioSquadra2(int punteggioSquadra2) {
        this.punteggioSquadra2 = punteggioSquadra2;
    }

    public String getSquadra1() {
        return squadra1;
    }

    public void setSquadra1(String squadra1) {
        this.squadra1 = squadra1;
    }

    public String getSquadra2() {
        return squadra2;
    }

    public void setSquadra2(String squadra2) {
        this.squadra2 = squadra2;
    }

    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }
}
