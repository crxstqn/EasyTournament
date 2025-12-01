package com.ingsw.easytournament.controller.visualizza_torneo;

import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GironeItalianaController {

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_stato;

    @FXML
    private Label label_titolo;

    @FXML
    private ListView<Incontro> list_view_incontri;

    @FXML
    private ChoiceBox<String> selettore_giornate;

    @FXML
    private Tab sezione_classifica;

    @FXML
    private Tab sezione_incontri;

    @FXML
    private TableView<Squadra> table_view_classifica;

    @FXML
    private TableColumn<Squadra, Integer> col_pareggi;

    @FXML
    private TableColumn<Squadra, Integer> col_partite_giocate;

    @FXML
    private TableColumn<Squadra, Integer> col_posizione;

    @FXML
    private TableColumn<Squadra, Integer> col_punti;

    @FXML
    private TableColumn<Squadra, Integer> col_sconfitte;

    @FXML
    private TableColumn<Squadra, String> col_squadre;

    @FXML
    private TableColumn<Squadra, Integer> col_vittorie;

    private Torneo torneo;

    private Map<Integer,List<Incontro>> incontri;
    private Map<Integer, int[]> statistiche = new HashMap<>();

    public void initialize(){
        this.torneo = SessioneTorneo.getInstance().getBozzaTorneo();
        this.label_titolo.setText(torneo.getNome());
        boolean torneoIniziato = (this.torneo.getData().isBefore(LocalDate.now()) || this.torneo.getData().isEqual(LocalDate.now()));
        if (torneoIniziato){
            this.label_stato.setText("Stato: In corso");
        }
        else {
            this.label_stato.setText("Data di inizio: " + this.torneo.getData().toString());
        }
        //aggiungo un listener al selettore delle giornate al cambiamento aggiorno la lista degli incontri
        this.selettore_giornate.valueProperty().addListener((observable, vecchioValore, nuovoValore) -> {
            if (nuovoValore != vecchioValore){
                aggiornaListaIncontri(nuovoValore);
            }
        });

        caricaIncontri();
        configuraClassifica();
        calcolaAggiornaClassifica();
    }

    private void aggiornaListaIncontri(String nuovoValore) {
        String[] giornataSplit = nuovoValore.split(" ");
        int giornata = Integer.parseInt(giornataSplit[1]);
        list_view_incontri.getItems().setAll(torneo.getIncontri().get(giornata));
    }

    private void caricaIncontri(){
        this.incontri = torneo.getIncontri();

        //logica per selezionare la prima giornata senza incontri completati
        boolean trovatoIncontroNonCompletato = false;
        for (int giornata : incontri.keySet()) {
            selettore_giornate.getItems().add("Giornata " + giornata);
            for (Incontro incontro : incontri.get(giornata)) {
                if (!trovatoIncontroNonCompletato) {
                    if (incontro.getPunteggioSquadra1() == -1 || incontro.getPunteggioSquadra2() == -1) {
                        selettore_giornate.getSelectionModel().select(giornata-1);
                        trovatoIncontroNonCompletato = true;
                    }
                }
            }
        }
        if (!trovatoIncontroNonCompletato){
            selettore_giornate.getSelectionModel().selectLast();
        }
    }

    @FXML
    void aggiornaRisultato(ActionEvent event) {
        Incontro incontro = list_view_incontri.getSelectionModel().getSelectedItem();
        if (incontro == null){
            mostraAlert("Non hai selezionato alcun incontro!");
        }
        else {
            if (LocalDate.now().isAfter(torneo.getData())) {
                SessioneAggiornamentoIncontro.getInstance().pulisciSessione();
                SessioneAggiornamentoIncontro.getInstance().setIncontro(incontro);
                SceneChanger.getInstance().createModalityStage("/com/ingsw/easytournament/fxml/visualizza_torneo/aggiorna_incontro.fxml", "/com/ingsw/easytournament/css/aggiorna_incontro.css", null);
            }
            else {
                mostraAlert("Il torneo deve ancora iniziare!");
            }
        }
        calcolaAggiornaClassifica();
        aggiornaListaIncontri(selettore_giornate.getValue());

    }

    private void configuraClassifica() {
        //colonna nome squadre me le prendo da ogni oggetto
        col_squadre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        // la posizione la prendo in base alla riga
        col_posizione.setCellValueFactory(data ->
                new SimpleIntegerProperty(table_view_classifica.getItems().indexOf(data.getValue()) + 1).asObject()
        );

        //ogni colonna delle statistiche legge dalla mappa delle statistiche
        col_punti.setCellValueFactory(data -> getStat(data.getValue().getId(), 0));
        col_partite_giocate.setCellValueFactory(data -> getStat(data.getValue().getId(), 1));
        col_vittorie.setCellValueFactory(data -> getStat(data.getValue().getId(), 2));
        col_pareggi.setCellValueFactory(data -> getStat(data.getValue().getId(), 3));
        col_sconfitte.setCellValueFactory(data -> getStat(data.getValue().getId(), 4));
    }

    private ObjectProperty<Integer> getStat(int idSquadra, int indiceStat) {
        int[] stats = statistiche.get(idSquadra);
        if (stats != null){
            return new SimpleIntegerProperty(stats[indiceStat]).asObject();
        }
        else {
            return new SimpleIntegerProperty(0).asObject();
        }
    }

    private void calcolaAggiornaClassifica(){
        //svuoto la mappa e metto un id e tutti 0 inizialmente
        this.statistiche.clear();
        for (Squadra s: torneo.getSquadre()){
            statistiche.put(s.getId(), new int[]{0,0,0,0,0});
        }

        //mi prendo le regole
        GironeItaliana regole = ((GironeItaliana) torneo.getModalita());
        int puntiVittoria = regole.getPuntiVittoria();
        int puntiPareggio = regole.getPuntiPareggio();
        int puntiSconfitta = regole.getPuntiSconfitta();

        //per ogni incontro aggiorno le statistiche
        for (List<Incontro> lista: incontri.values()){
            for (Incontro incontro: lista){
                if (incontro.getPunteggioSquadra1() != -1 && incontro.getPunteggioSquadra2() != -1){
                    aggiornaStatisticaSquadra(incontro.getSquadra1().getId(),incontro.getPunteggioSquadra1(), incontro.getPunteggioSquadra2(),puntiVittoria,puntiPareggio,puntiSconfitta);
                    aggiornaStatisticaSquadra(incontro.getSquadra2().getId(),incontro.getPunteggioSquadra2(), incontro.getPunteggioSquadra1(),puntiVittoria,puntiPareggio,puntiSconfitta);
                }
            }
        }

        List<Squadra> listaOrdinata = new ArrayList<>(torneo.getSquadre());
        listaOrdinata.sort((s1,s2)-> {
            int p1 = statistiche.get(s1.getId())[0];
            int p2 = statistiche.get(s2.getId())[0];
            return p2-p1;
        });

        table_view_classifica.getItems().setAll(listaOrdinata);
        table_view_classifica.refresh();
    }

    private void aggiornaStatisticaSquadra(int idSquadra, int punteggioSquadra1, int punteggioSquadra2, int puntiVittoria, int puntiPareggio, int puntiSconfitta){
        int[] stats = statistiche.get(idSquadra);
        stats[1]++; //aggiorno partite giocate
        if (punteggioSquadra1 > punteggioSquadra2) {
            stats[0]+=puntiVittoria;
            stats[2]++;
        }
        else if (punteggioSquadra1 == punteggioSquadra2) {
            stats[0]+=puntiPareggio;
            stats[3]++;
        }
        else {
            stats[0]+=puntiSconfitta;
            stats[4]++;
        }
    }
    @FXML
    void tornaHome(ActionEvent event) {
        SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/home.css",true);
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
