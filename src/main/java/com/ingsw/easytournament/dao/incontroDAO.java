package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface incontroDAO {
    public boolean salvaIncontro(int idTorneo, Map<Integer, List<Incontro>> incontri);

    Map<Integer, List<Incontro>> getIncontriDaTorneo(Torneo torneo);

    boolean aggiornaIncontro(Incontro incontro);
}
