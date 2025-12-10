package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Torneo;

import java.util.List;

public interface torneoDAO {
    List<Torneo> getTorneiByUtente(int idUtente);
    boolean eliminaTorneo(int idTorneo);
    boolean creaTorneo(Torneo torneo);
//    boolean modificaTorneo(Torneo torneo);
//    boolean torneoEsistente(String nome);
}
