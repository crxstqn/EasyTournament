package com.ingsw.easytournament.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Torneo {
    int id;
    String nome;
    LocalDate data;
    int id_modalità;
    int id_utente;
    
    Modalita modalita;
    
    List<String> squadre = new ArrayList<>();
    

    public Torneo(LocalDate data, int id, int id_utente, String nome, int m) {
        this.data = data;
        this.id = id;
        this.id_utente = id_utente;
        this.id_modalità = m;
        this.nome = nome;
    }

    public Torneo(int id_modalità, int id_utente, String nome, LocalDate data, List<String> squadre) {
        this.id_modalità = id_modalità;
        this.id_utente = id_utente;
        this.nome = nome;
        this.data = data;
        this.squadre = squadre;

        configuraModalita();
    }

    private void configuraModalita(){
        switch (this.id_modalità){
            case 0: {
                this.modalita = new GironeItaliana("Girone all'italiana");
                break;
            }
            case 1: {
                this.modalita = new EliminazioneDiretta("Eliminazione diretta");
                break;
            }
            case 2: {
                this.modalita = new GironiPlayOff("Gironi + Play-off");
                break;
            }
        }
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
        return id_modalità;
    }

    public void setModalità(int modalità) {
        this.id_modalità = modalità;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}



