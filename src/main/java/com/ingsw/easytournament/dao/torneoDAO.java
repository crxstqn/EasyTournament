package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Torneo;

import java.util.List;

public interface torneoDAO {
    public List<Torneo> getTorneiByUtente(int idUtente);
    public boolean eliminaTorneo(int idTorneo);
    public boolean creaTorneo(Torneo torneo);
    public boolean torneoEsistente(String nome, int idUtente);
//    public boolean modificaTorneo(Torneo torneo);
}
