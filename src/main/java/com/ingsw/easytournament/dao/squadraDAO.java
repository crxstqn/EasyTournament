package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Squadra;

import java.util.List;

public interface squadraDAO {
    void salvaSquadra(int idTorneo, List<Squadra> squadra);

    List<Squadra> getSquadreDaTorneo(int id);
}
