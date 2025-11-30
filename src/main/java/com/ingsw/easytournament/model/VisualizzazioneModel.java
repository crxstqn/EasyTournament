package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;

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
        return DatabaseConnessione.getInstance().getIncontroDAO().aggiornaIncontro(incontro);
    }
}
