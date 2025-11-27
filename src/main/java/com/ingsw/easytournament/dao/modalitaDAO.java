package com.ingsw.easytournament.dao;

public interface modalitaDAO {
    public boolean creaModalita(String nome,int puntiVittoria, int puntiPareggio, int puntiSconfitta, boolean andataEritorno, boolean finalina);
}
