package com.ingsw.easytournament.model;

public class GironeItaliana extends Modalita{
    private int puntiVittoria;
    private int puntiSconfitta;
    private int puntiPareggio;
    boolean andataEritorno;

    GironeItaliana(String nome){
        super(nome);
    }

    public boolean isAndataEritorno() {
        return andataEritorno;
    }

    public void setAndataEritorno(boolean andataEritorno) {
        this.andataEritorno = andataEritorno;
    }

    public int getPuntiPareggio() {
        return puntiPareggio;
    }

    public void setPuntiPareggio(int puntiPareggio) {
        this.puntiPareggio = puntiPareggio;
    }

    public int getPuntiSconfitta() {
        return puntiSconfitta;
    }

    public void setPuntiSconfitta(int puntiSconfitta) {
        this.puntiSconfitta = puntiSconfitta;
    }

    public int getPuntiVittoria() {
        return puntiVittoria;
    }

    public void setPuntiVittoria(int puntiVittoria) {
        this.puntiVittoria = puntiVittoria;
    }
}
