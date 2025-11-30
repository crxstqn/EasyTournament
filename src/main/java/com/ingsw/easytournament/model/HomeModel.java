package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SessioneUtente;

import java.util.List;

public class HomeModel {
    public static List<Torneo> getTornei() {
        return DatabaseConnessione.getInstance().getTorneoDAO().getTorneiByUtente(SessioneUtente.getInstance().getUserId());
    }

    public static boolean eliminaTorneo(int idTorneo) {
        return DatabaseConnessione.getInstance().getTorneoDAO().eliminaTorneo(idTorneo);
    }

    public static Boolean creaTorneo(Torneo torneo) {
        boolean salvataggioTorneo =  DatabaseConnessione.getInstance().getTorneoDAO().creaTorneo(torneo);
        torneo.inizializzaIncontri();
        boolean salvataggioIncontro = DatabaseConnessione.getInstance().getIncontroDAO().salvaIncontro(torneo.getId(), torneo.getIncontri());
        return salvataggioTorneo && salvataggioIncontro;
    }
}
