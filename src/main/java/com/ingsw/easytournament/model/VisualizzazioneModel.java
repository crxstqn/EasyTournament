package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import javafx.scene.SubScene;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class VisualizzazioneModel {
    private static VisualizzazioneModel instance;

    private VisualizzazioneModel() {}

    public static VisualizzazioneModel getInstance() {
        if (instance == null) {
            instance = new VisualizzazioneModel();
        }
        return instance;
    }

    public boolean aggiornaIncontro(Incontro incontro){
        boolean returna =  DatabaseConnessione.getInstance().getIncontroDAO().aggiornaIncontro(incontro);
        System.out.println("HO RETURNATO: " + returna);
        return returna;
    }

    public boolean avanzaTurno(Torneo torneo){
        if (torneo.getIdModalità() != 1){
            return false;
        }

        Pair<Integer, List<Incontro>> nuoviIncontri = torneo.avanzaTurno();
        Pair<Integer, Incontro> incontroFinalina = torneo.getFinalina();
        if (incontroFinalina != null){
            DatabaseConnessione.getInstance().getIncontroDAO().salvaSingoloIncontro(torneo.getId(), incontroFinalina);
        }

        DatabaseConnessione.getInstance().getIncontroDAO().salvaNuoviIncontri(nuoviIncontri.getKey(), nuoviIncontri.getValue());
        return true;

    };
}
