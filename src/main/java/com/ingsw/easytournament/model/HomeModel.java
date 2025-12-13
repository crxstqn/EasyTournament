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
        if (salvataggioTorneo){
            torneo.inizializzaIncontri();
            return DatabaseConnessione.getInstance().getIncontroDAO().salvaIncontro(torneo.getId(), torneo.getIncontri());
        }
        return false;
    }

    public static boolean torneoEsistente(String nomeTorneo, int idUtente){
        return DatabaseConnessione.getInstance().getTorneoDAO().torneoEsistente(nomeTorneo, idUtente);
    }

    public static Boolean salvaTorneo(Torneo torneo) {
        boolean salvataggioTorneo = DatabaseConnessione.getInstance().getTorneoDAO().modificaTorneo(torneo);
        if (salvataggioTorneo) {
            torneo.inizializzaIncontri();
            return DatabaseConnessione.getInstance().getIncontroDAO().salvaIncontro(torneo.getId(), torneo.getIncontri());
        }
        return false;
    }
}
