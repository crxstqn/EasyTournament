package com.ingsw.easytournament.model;

public class GironiPlayOff extends Modalita{
    private int numeroGironi;
    private int numSquadreGirone;
    private int vincitoriPerGirone;
    private int puntiVittoria;
    private int puntiSconfitta;
    private int puntiPareggio;
    boolean andataEritorno;
    boolean finalina;

    public GironiPlayOff(String nome){
        super(nome);
        puntiVittoria = 3;
        puntiPareggio = 1;
        puntiSconfitta = 0;
        numeroGironi = 2;
        vincitoriPerGirone = 1;
        numSquadreGirone = 2;
    }

    @Override
    public String getDescrizione() {
        String andataEritorno = this.andataEritorno ? "SI" : "NO";
        String finalina = this.finalina ? "SI" : "NO";

        return "Punti vittoria: " + puntiVittoria + "\nPunti pareggio: " + puntiPareggio + "\nPunti sconfitta: " + puntiSconfitta +
                "\nNumero di squadre per girone: " + numSquadreGirone + "\nNumero gironi: " + numeroGironi + "\nVincitori per girone: " + vincitoriPerGirone +
                "\nAndata e ritorno: " + andataEritorno + "\nFinalina: " + finalina;
    }

    public int getNumeroGironi() {
        return numeroGironi;
    }

    public void setNumeroGironi(int numeroGironi) {
        this.numeroGironi = numeroGironi;
    }

    public int getNumSquadreGirone() {
        return numSquadreGirone;
    }

    public void setNumSquadreGirone(int numSquadreGirone) {
        this.numSquadreGirone = numSquadreGirone;
    }

    public int getVincitoriPerGirone() {
        return vincitoriPerGirone;
    }

    public void setVincitoriPerGirone(int vincitoriPerGirone) {
        this.vincitoriPerGirone = vincitoriPerGirone;
    }

    public int getPuntiVittoria() {
        return puntiVittoria;
    }
    public void setPuntiVittoria(int puntiVittoria) {
        this.puntiVittoria = puntiVittoria;
    }
    public int getPuntiSconfitta() {
        return puntiSconfitta;
    }
    public void setPuntiSconfitta(int puntiSconfitta) {
        this.puntiSconfitta = puntiSconfitta;
    }
    public int getPuntiPareggio() {
        return puntiPareggio;
    }
    public void setPuntiPareggio(int puntiPareggio) {
        this.puntiPareggio = puntiPareggio;
    }
    public boolean isAndataEritorno() {
        return andataEritorno;
    }
    public void setAndataEritorno(boolean andataEritorno) {
        this.andataEritorno = andataEritorno;
    }
    public boolean isFinalina() {
        return finalina;
    }
    public void setFinalina(boolean finalina) {
        this.finalina = finalina;
    }
}
