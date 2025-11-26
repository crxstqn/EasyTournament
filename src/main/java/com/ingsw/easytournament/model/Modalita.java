package com.ingsw.easytournament.model;

public abstract class Modalita {
    protected String nome;

    Modalita(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract String getDescrizione();
}
