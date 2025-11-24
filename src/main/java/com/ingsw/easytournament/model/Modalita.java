package com.ingsw.easytournament.model;

public abstract class Modalita {
    protected int idModalita;
    protected String nome;

    Modalita(int idModalita, String nome) {
        this.idModalita = idModalita;
        this.nome = nome;
    }
}
