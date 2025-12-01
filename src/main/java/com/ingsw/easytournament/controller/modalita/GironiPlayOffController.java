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
        torneo = SessioneTorneo.getInstance().getBozzaTorneo();
        bozzaModalita = torneo.getModalita();
        spinner_vittoria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiVittoria()));
        spinner_pareggio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiPareggio()));
        spinner_sconfitta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, ((GironiPlayOff) bozzaModalita).getPuntiSconfitta()));

        scelta_andata_ritorno.setSelected(((GironiPlayOff) bozzaModalita).isAndataEritorno());
        scelta_finalina.setSelected(((GironiPlayOff) bozzaModalita).isFinalina());

        numero_gironi.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, ((GironiPlayOff) bozzaModalita).getNumeroGironi()));
        numero_squadre_girone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, (torneo.getSquadre().size())/2));
        numero_vincitrici_girone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, ((GironiPlayOff) bozzaModalita).getVincitoriPerGirone()));
    }

    @FXML
    void mostraRiepilogo(ActionEvent event) {
        int puntiVittoria = spinner_vittoria.getValue();
        int puntiPareggio = spinner_pareggio.getValue();
        int puntiSconfitta = spinner_sconfitta.getValue();
        boolean andataEritorno = scelta_andata_ritorno.isSelected();
        boolean Finalina = scelta_finalina.isSelected();

        int numeroGironi = numero_gironi.getValue();
        int numeroSquadre = numero_squadre_girone.getValue();
        int numeroVincitori = numero_vincitrici_girone.getValue();

        //controlliamo
        if ( 2*numeroGironi < numeroSquadre){
            mostraAlert("Il numero di gironi inserito non è valido per il numero di squadre inserito");
            return;

        }

        if (numeroGironi * numeroSquadre < SessioneTorneo.getInstance().getBozzaTorneo().getSquadre().size()){
            mostraAlert("Non hai inserito tutte le squadre per il numero di gironi inserito");
        }

        int numeroVincitoriTotali = numeroGironi * numeroVincitori;
        boolean èUnaPotenzaDi2 = (numeroVincitoriTotali > 0) && ((numeroVincitoriTotali & (numeroVincitoriTotali - 1)) == 0);
        if (!èUnaPotenzaDi2){
            mostraAlert("Il numero di squadre passanti per girone non è valido per il numero di gironi inserito");
            return;
        }

        //copiamo
        GironiPlayOff castModalita =  (GironiPlayOff) bozzaModalita;
        castModalita.setPuntiVittoria(puntiVittoria);
        castModalita.setPuntiPareggio(puntiPareggio);
        castModalita.setPuntiSconfitta(puntiSconfitta);

        castModalita.setAndataEritorno(andataEritorno);
        castModalita.setFinalina(Finalina);

        castModalita.setNumeroGironi(numeroGironi);
        castModalita.setNumSquadreGirone(numeroSquadre);
        castModalita.setVincitoriPerGirone(numeroVincitori);

        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_torneo.css", button_indietro.getScene());
    }

    private void mostraAlert(String testoErrore) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Attenzione");
        alert.setGraphic(null);
        alert.setContentText(testoErrore);
        alert.showAndWait();
    }

}
