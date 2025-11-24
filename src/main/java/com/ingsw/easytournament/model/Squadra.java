package com.ingsw.easytournament.model;

public class Squadra {
    private int id;
    private String nome;
    private String idTorneo;

    public Squadra(String nome, int id, String idTorneo) {
        this.nome = nome;
        this.id = id;
        this.idTorneo = idTorneo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getIdTorneo() {
        return idTorneo;
    }
}
