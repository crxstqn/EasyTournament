package com.ingsw.easytournament.model;

public class EliminazioneDiretta extends Modalita{
    boolean finalina;

    public EliminazioneDiretta(String nome){
        super(nome);
    }

    @Override
    public String getDescrizione() {
        String risultato;
        if(finalina) risultato = "SI";
        else risultato = "NO";

        return "Finale terzo quarto posto: " + risultato;
    }

    public boolean isFinalina() {
        return finalina;
    }

    public void setFinalina(boolean finalina) {
        this.finalina = finalina;
    }
}
