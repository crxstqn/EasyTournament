package com.ingsw.easytournament.model;

public class EliminazioneDiretta extends Modalita{
    boolean finalina;

    public EliminazioneDiretta(String nome){
        super(nome);
    }

    public boolean isFinalina() {
        return finalina;
    }

    public void setFinalina(boolean finalina) {
        this.finalina = finalina;
    }
}
