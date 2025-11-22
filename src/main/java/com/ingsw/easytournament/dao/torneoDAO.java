package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Torneo;

import java.util.List;

public interface torneoDAO {
    public List<Torneo> getTorneiByUtente(int idUtente);
    public boolean eliminaTorneo(int idUtente, int idTorneo);
}
