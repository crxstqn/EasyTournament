package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SessioneUtente;

import java.util.List;

public class HomeModel {
    public static List<Torneo> getTornei() {
        return DatabaseConnessione.getInstance().getTorneoDAO().getTorneiByUtente(SessioneUtente.getInstance().getUserId());
    }

    public static boolean eliminaTorneo(int idTorneo) {
        return DatabaseConnessione.getInstance().getTorneoDAO().eliminaTorneo(SessioneUtente.getInstance().getUserId(), idTorneo);
    }

    public static Boolean creaTorneo(Torneo torneo) {
        return DatabaseConnessione.getInstance().getTorneoDAO().creaTorneo(torneo);
    }
}
