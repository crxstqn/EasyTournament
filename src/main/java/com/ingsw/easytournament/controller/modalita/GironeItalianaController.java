package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.GironeItaliana;
import com.ingsw.easytournament.model.Modalita;
import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class GironeItalianaController{

    @FXML
    private Button button_avanti;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_titolo;

    @FXML
    private CheckBox scelta_andata_ritorno;

    @FXML
    private Spinner<Integer> spinner_pareggio;

    @FXML
    private Spinner<Integer> spinner_sconfitta;

    @FXML
    private Spinner<Integer> spinner_vittoria;
    private Modalita bozzaModalita;

    public void initialize() {
        bozzaModalita = SessioneCreazioneTorneo.getInstance().getBozzaTorneo().getModalita();
        spinner_vittoria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiVittoria()));
        spinner_pareggio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiPareggio()));
        spinner_sconfitta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiSconfitta()));
        scelta_andata_ritorno.setSelected(((GironeItaliana) bozzaModalita).isAndataEritorno());
    }

        @FXML
    void mostraRiepilogo(ActionEvent event) {
        String puntiVittoria = spinner_vittoria.getEditor().getText();
        String puntiPareggio = spinner_pareggio.getEditor().getText();
        String puntiSconfitta = spinner_sconfitta.getEditor().getText();
        boolean andataEritorno = scelta_andata_ritorno.isSelected();

        //copiamo
        GironeItaliana castModalita =  (GironeItaliana) bozzaModalita;
        castModalita.setPuntiVittoria(Integer.parseInt(puntiVittoria));
        castModalita.setPuntiPareggio(Integer.parseInt(puntiPareggio));
        castModalita.setPuntiSconfitta(Integer.parseInt(puntiSconfitta));
        castModalita.setAndataEritorno(andataEritorno);

        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_torneo.css", button_indietro.getScene());
    }

}
