package com.ingsw.easytournament.model;

import java.time.LocalDate;
import java.util.Date;

public class Torneo {
    int id;
    String nome;
    LocalDate data;
    int modalità;
    int id_utente;

    public Torneo(LocalDate data, int id, int id_utente, String nome, int m) {
        this.data = data;
        this.id = id;
        this.id_utente = id_utente;
        this.modalità = m;
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getModalità() {
        return modalità;
    }

    public void setModalità(int modalità) {
        this.modalità = modalità;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}



