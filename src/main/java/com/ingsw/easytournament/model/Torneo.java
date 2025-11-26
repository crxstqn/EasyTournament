package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.SessioneUtente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Torneo {
    private int id;
    private String nome;
    private LocalDate data;
    private int id_modalità;
    private int id_utente;
    private Modalita modalita;
    private List<String> squadre;


    public Torneo(LocalDate data, int id, int id_utente, String nome, int m) {
        this.data = data;
        this.id = id;
        this.id_utente = id_utente;
        this.id_modalità = m;
        this.nome = nome;
    }

    public Torneo(String nome, LocalDate data, int id_modalità, List<String> squadre) {
        this.id_utente = SessioneUtente.getInstance().getUserId();
        this.nome = nome;
        this.data = data;
        this.squadre = squadre;
        this.id_modalità = id_modalità;

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

    public String getDescrizione(){
        return "Nome torneo: " + this.nome + "\nData di inzio: " + this.data.toString() + "\nModalità: " + this.modalita.getNome() + "\n" + this.modalita.getDescrizione();
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

    public int getIdModalità() {
        return id_modalità;
    }

    public Modalita getModalita() {
        return modalita;
    }

    public void setIdModalità(int modalità) {
        this.id_modalità = modalità;
        configuraModalita();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getSquadre() {
        return squadre;
    }

    public void setSquadre(List<String> squadre) {
        this.squadre = squadre;
    }
}



