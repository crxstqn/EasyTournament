package com.ingsw.easytournament.controller.visualizza_torneo;

import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EliminazioneDirettaController {

    @FXML
    private Button aggiorna_risultato_button;

    @FXML
    private Button avanza_turno_button;

    @FXML
    private Button button_indietro;

    @FXML
    private HBox hbox_top;

    @FXML
    private Label label_stato;

    @FXML
    private Label label_titolo;

    @FXML
    private ListView<Incontro> list_view_incontri;

    @FXML
    private ChoiceBox<String> selettore_turno;

    private Torneo torneo;
    private Map<Integer, List<Incontro>> incontri;

    public void initialize() {
        this.torneo = SessioneTorneo.getInstance().getBozzaTorneo();
        this.label_titolo.setText(torneo.getNome());

        boolean torneoIniziato = (this.torneo.getData().isBefore(LocalDate.now()) || this.torneo.getData().isEqual(LocalDate.now()));
        if (torneoIniziato) {
            this.label_stato.setText("Stato: In corso");
        } else {
            this.label_stato.setText("Data di inizio: " + this.torneo.getData().toString());
        }

        //aggiungo un listener al selettore delle giornate al cambiamento aggiorno la lista degli incontri
        this.selettore_turno.valueProperty().addListener((observable, vecchioValore, nuovoValore) -> {
            if (nuovoValore!= null && !nuovoValore.equals(vecchioValore)) {
                aggiornaListaIncontri(nuovoValore);
            }
        });

        caricaIncontri();
    }

    @FXML
    void aggiornaRisultato(ActionEvent event) {
        Incontro incontro = list_view_incontri.getSelectionModel().getSelectedItem();
        if (incontro == null) {
            mostraAlert("Non hai selezionato alcun incontro!");
        } else {
            if (LocalDate.now().isAfter(torneo.getData()) || LocalDate.now().isEqual(torneo.getData())) {
                SessioneAggiornamentoIncontro.getInstance().pulisciSessione();
                SessioneAggiornamentoIncontro.getInstance().setIncontro(incontro);
                AggiornaIncontroController.setPareggioPossibile(false);
                SceneChanger.getInstance().createModalityStage("/com/ingsw/easytournament/fxml/visualizza_torneo/aggiorna_incontro.fxml", "/com/ingsw/easytournament/css/aggiorna_incontro.css", null);
            } else {
                mostraAlert("Il torneo deve ancora iniziare!");
            }
        }
        aggiornaListaIncontri(selettore_turno.getValue());

    }

    @FXML
    void avanzaTurno(ActionEvent event) {
        //aggiunto
        if (LocalDate.now().isBefore(torneo.getData())){
            mostraAlert("Il torneo deve ancora iniziare!");
            return;
        }

        int ultimoTurno = Collections.min(incontri.keySet());
        // se l'ultimo turno è 0 e 1 , vuol dire che l'ultimo turno è stato già raggiunto
        if (ultimoTurno <= 1){
            mostraAlert("Il torneo ha già raggiunto il turno finale!");
            this.avanza_turno_button.setDisable(true);
            return;
        }

        for (int turno : incontri.keySet()) {
            for (Incontro incontro : incontri.get(turno)) {
                if (incontro.getPunteggioSquadra1() == -1 || incontro.getPunteggioSquadra2() == -1) {
                    mostraAlert("Tutti gli incontri devono essere completati per avanzare al turno successivo!");
                    return;
                }
            }
        }

        System.out.println("Avanzamento turno!");
        VisualizzazioneModel.getInstance().avanzaTurno(this.torneo);
        caricaIncontri();
    }

    @FXML
    void tornaHome(ActionEvent event) {
        SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/home.css",true);
    }


    private void aggiornaListaIncontri(String nuovoValore) {
        if (nuovoValore.equals("semifinale")) {
            System.out.println("Semifinale!");
            list_view_incontri.getItems().setAll(torneo.getIncontri().get(2));
        }
        else if (nuovoValore.equals("finale")){
            list_view_incontri.getItems().setAll(torneo.getIncontri().get(1));
        }
        else if (nuovoValore.equals("finale 3/4")){
            list_view_incontri.getItems().setAll(torneo.getIncontri().get(0));
        }
        else {
            String[] turnoSplit = nuovoValore.split(" ");
            int turno = Integer.parseInt(turnoSplit[0]);
            list_view_incontri.getItems().setAll(torneo.getIncontri().get(turno));
        }
    }

    private void caricaIncontri() {
        this.incontri = torneo.getIncontri();
        selettore_turno.getItems().clear();

        if (incontri.containsKey(2)){
            selettore_turno.getItems().add("semifinale");
        }
        if (incontri.containsKey(1)) {
            System.out.println("Finale!");
            selettore_turno.getItems().add("finale");
        }
        if (incontri.containsKey(0)){
            System.out.println("3/4!");
            selettore_turno.getItems().add("finale 3/4");
        }

        //logica per selezionare il primo turno senza incontri completati
        for (int turno : incontri.keySet()) {
            System.out.println(turno);
            if (turno != 0 && turno != 1 && turno != 2) {
                selettore_turno.getItems().add(turno + " ° finale");
            }
        }
        selettore_turno.getSelectionModel().selectFirst();
    }

    private void mostraAlert(String testoErrore) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Attenzione");
        alert.setGraphic(null);
        alert.setContentText(testoErrore);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/com/ingsw/easytournament/css/alert.css");
        alert.showAndWait();
    }
}

