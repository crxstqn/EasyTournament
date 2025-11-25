package com.ingsw.easytournament.model;

public class GironiPlayOff extends Modalita{
    int numeroGironi;
    int numSquadreGirone;
    int vincitoriPerGirone;

    public GironiPlayOff(String nome){
        super(nome);
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
}
