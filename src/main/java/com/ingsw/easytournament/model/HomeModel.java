package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SessioneUtente;

import java.util.List;

public class HomeModel {
    public static List<Torneo> getTornei() {
        return DatabaseConnessione.getInstance().getTorneoDAO().getTorneiByUtente(SessioneUtente.getInstance().getUserId());
    }
}
