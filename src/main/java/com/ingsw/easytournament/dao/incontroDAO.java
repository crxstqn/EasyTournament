package com.ingsw.easytournament.dao;

import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface incontroDAO {
    boolean salvaIncontro(int idTorneo, Map<Integer, List<Incontro>> incontri);

    boolean salvaSingoloIncontro(int idTorneo, Pair<Integer, Incontro> incontroFinalina);

    Map<Integer, List<Incontro>> getIncontriDaTorneo(Torneo torneo);

    boolean aggiornaIncontro(Incontro incontro);

    boolean salvaNuoviIncontri(int turno, List<Incontro> nuoviIncontri);
}
