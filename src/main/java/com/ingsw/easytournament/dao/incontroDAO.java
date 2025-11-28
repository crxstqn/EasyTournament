package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface incontroDAO {
    public boolean salvaIncontro(int idTorneo, Map<Integer, List<Incontro>> incontri);
}
