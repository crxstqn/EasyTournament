package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.GironeItaliana;
import com.ingsw.easytournament.model.Modalita;
import com.ingsw.easytournament.model.SessioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GironeItalianaController implements ControlloreOpzioni {

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
        bozzaModalita = SessioneTorneo.getInstance().getBozzaTorneo().getModalita();
        spinner_vittoria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiVittoria()));
        spinner_pareggio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiPareggio()));
        spinner_sconfitta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironeItaliana) bozzaModalita).getPuntiSconfitta()));
        scelta_andata_ritorno.setSelected(((GironeItaliana) bozzaModalita).isAndataEritorno());
        //boolean torneoEsistente = DatabaseConnessione.getInstance().
    }

        @FXML
    void mostraRiepilogo(ActionEvent event) {
        int puntiVittoria = spinner_vittoria.getValue();
        int puntiPareggio = spinner_pareggio.getValue();
        int puntiSconfitta = spinner_sconfitta.getValue();
        boolean andataEritorno = scelta_andata_ritorno.isSelected();

        //copiamo
        GironeItaliana castModalita =  (GironeItaliana) bozzaModalita;
        castModalita.setPuntiVittoria(puntiVittoria);
        castModalita.setPuntiPareggio(puntiPareggio);
        castModalita.setPuntiSconfitta(puntiSconfitta);
        castModalita.setAndataEritorno(andataEritorno);

        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @Override
    public void caricaDati(Torneo torneo) {
        //nascondo pulsanti, servono solo in modifica
        if (button_avanti != null) button_avanti.setVisible(false);
        if (button_indietro != null) button_indietro.setVisible(false);

        if (torneo.getModalita() instanceof GironeItaliana) {
            GironeItaliana g = (GironeItaliana) torneo.getModalita();

            spinner_vittoria.getValueFactory().setValue(g.getPuntiVittoria());
            spinner_pareggio.getValueFactory().setValue(g.getPuntiPareggio());
            spinner_sconfitta.getValueFactory().setValue(g.getPuntiSconfitta());
            scelta_andata_ritorno.setSelected(g.isAndataEritorno());
        }
    }

    @Override
    public void salvaDati(Torneo torneo) {
        if (torneo.getModalita() instanceof GironeItaliana) {
            GironeItaliana g = (GironeItaliana) torneo.getModalita();

            g.setPuntiVittoria(spinner_vittoria.getValue());
            g.setPuntiPareggio(spinner_pareggio.getValue());
            g.setPuntiSconfitta(spinner_sconfitta.getValue());
            g.setAndataEritorno(scelta_andata_ritorno.isSelected());
        }
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_torneo.css", button_indietro.getScene());
    }

}
