package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GironiPlayOffController{

    @FXML
    private Button button_avanti;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_titolo;

    @FXML
    private Spinner<Integer> numero_gironi;

    @FXML
    private Spinner<Integer> numero_squadre_girone;

    @FXML
    private Spinner<Integer> numero_vincitrici_girone;

    @FXML
    private CheckBox scelta_andata_ritorno;

    @FXML
    private CheckBox scelta_finalina;

    @FXML
    private Spinner<Integer> spinner_pareggio;

    @FXML
    private Spinner<Integer> spinner_sconfitta;

    @FXML
    private Spinner<Integer> spinner_vittoria;

    private Torneo torneo;
    private Modalita bozzaModalita;

    public void initialize() {
        bozzaModalita = SessioneCreazioneTorneo.getInstance().getBozzaTorneo().getModalita();
        spinner_vittoria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiVittoria()));
        spinner_pareggio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiPareggio()));
        spinner_sconfitta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiSconfitta()));

        scelta_andata_ritorno.setSelected(((GironiPlayOff) bozzaModalita).isAndataEritorno());
        scelta_finalina.setSelected(((GironiPlayOff) bozzaModalita).isFinalina());

        numero_gironi.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, ((GironiPlayOff) bozzaModalita).getNumeroGironi()));
        numero_squadre_girone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, ((GironiPlayOff) bozzaModalita).getNumSquadreGirone()));
        numero_vincitrici_girone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, ((GironiPlayOff) bozzaModalita).getVincitoriPerGirone()));
    }

    @FXML
    void mostraRiepilogo(ActionEvent event) {
        String puntiVittoria = spinner_vittoria.getEditor().getText();
        String puntiPareggio = spinner_pareggio.getEditor().getText();
        String puntiSconfitta = spinner_sconfitta.getEditor().getText();
        boolean andataEritorno = scelta_andata_ritorno.isSelected();
        boolean Finalina = scelta_finalina.isSelected();

        String numeroGironi = numero_gironi.getEditor().getText();
        String numeroSquadre = numero_squadre_girone.getEditor().getText();
        String numeroVincitori = numero_vincitrici_girone.getEditor().getText();

        //controlliamo
        //DA IMPLEMENTARE

        //copiamo
        GironiPlayOff castModalita =  (GironiPlayOff) bozzaModalita;
        castModalita.setPuntiVittoria(Integer.parseInt(puntiVittoria));
        castModalita.setPuntiPareggio(Integer.parseInt(puntiPareggio));
        castModalita.setPuntiSconfitta(Integer.parseInt(puntiSconfitta));

        castModalita.setAndataEritorno(andataEritorno);
        castModalita.setFinalina(Finalina);

        castModalita.setNumeroGironi(Integer.parseInt(numeroGironi));
        castModalita.setNumSquadreGirone(Integer.parseInt(numeroSquadre));
        castModalita.setVincitoriPerGirone(Integer.parseInt(numeroVincitori));

        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_torneo.css", button_indietro.getScene());
    }

}
