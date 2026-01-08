package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.controller.modalita.ControlloreOpzioni;
import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ModificaTorneoController {

    @FXML
    private AnchorPane box_destra;

    @FXML
    private Button button_aggiungi;

    @FXML
    private Button button_salva_modifiche;

    @FXML
    private DatePicker campo_data;

    @FXML
    private ChoiceBox<String> campo_modalita;

    @FXML
    private TextField campo_nome_torneo;

    @FXML
    private TextField campo_squadra;

    @FXML
    private Label label_gestione_squadre;

    @FXML
    private Label label_modifica_torneo;

    @FXML
    private Label label_squadre_inserite;

    @FXML
    private ListView<Squadra> list_view_squadre;

    private Torneo torneo;
    private final ObservableList<Squadra> elencoSquadre = FXCollections.observableArrayList();
    private ControlloreOpzioni controllerOpzioniCorrente;

    public void initialize(){
        this.torneo = SessioneTorneo.getInstance().getBozzaTorneo();

        this.campo_modalita.getItems().addAll("Girone all'italiana", "Eliminazione diretta");

        this.campo_nome_torneo.setText(torneo.getNome());
        this.campo_data.setValue(torneo.getData());

        this.elencoSquadre.addAll(torneo.getSquadre());
        this.list_view_squadre.setItems(elencoSquadre);
        label_squadre_inserite.textProperty().bind(
                Bindings.format("Squadre inserite: %d", Bindings.size(elencoSquadre))
        );

        list_view_squadre.setCellFactory(param -> new ListCell<Squadra>() {
            private final Label labelNome = new Label();
            // 1. Creiamo una regione "vuota" invisibile che farà da molla
            private final Region spacer = new Region();
            private final Button btnElimina = new Button("X");
            // Togliamo il '10' dal costruttore, lo spazio ora è dinamico
            private final HBox rootHBox = new HBox();

            {
                btnElimina.getStyleClass().add("button-elimina-squadra");

                // Allineamento verticale al centro. L'orizzontale lo gestisce lo spacer.
                rootHBox.setAlignment(javafx.geometry.Pos.CENTER);

                // 2. È lo SPACER che deve crescere e occupare tutto lo spazio disponibile
                // Spingerà la label a sinistra e il bottone a destra.
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // RIMOSSO: HBox.setHgrow(labelNome, Priority.ALWAYS); non serve più

                // 3. Aggiungiamo lo spacer IN MEZZO tra label e bottone
                rootHBox.getChildren().addAll(labelNome, spacer, btnElimina);

                // AZIONE DEL BOTTONE ELIMINA (INVARIATO)
                btnElimina.setOnAction(event -> {
                    Squadra squadraDaEliminare = getItem(); // Recupera il nome della squadra di questa riga
                    elencoSquadre.remove(squadraDaEliminare); // La rimuove dalla lista dati
                    // La ListView si aggiornerà automaticamente!
                });
            }

            @Override
            protected void updateItem(Squadra item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    // Se la riga è vuota, non mostrare nulla
                    setText(null);
                    setGraphic(null);
                    // È buona norma resettare lo stile per evitare artefatti grafici
                    //setStyle("");
                } else {
                    // Se c'è una squadra, configura la Label e mostra l'HBox personalizzato
                    labelNome.setText(item.getNome());
                    setText(null); // Nascondiamo il testo standard della cella
                    setGraphic(rootHBox); // Mostriamo il nostro HBox

                    // Opzionale: forza l'HBox a occupare tutta la larghezza della cella
                    rootHBox.prefWidthProperty().bind(getListView().widthProperty().subtract(20)); // subtract per padding/scroll
                }
            }
        });

        campo_modalita.getSelectionModel().selectedIndexProperty().addListener((obs, vecchio, nuovo) -> {
            caricaPannelloOpzioni(nuovo.intValue());
        });

        this.campo_modalita.getSelectionModel().select(this.torneo.getIdModalità());
    }

    private void caricaPannelloOpzioni(int idModalita) {
        String fxmlPath = "";
        if (idModalita != torneo.getIdModalità()) {
            torneo.setIdModalità(idModalita);
        }

        switch (idModalita) {
            case 0: //Girone italiana
                fxmlPath = "/com/ingsw/easytournament/fxml/creazione_modalita/girone_all_italiana.fxml";
                break;
            case 1: //Eliminazione diretta
                fxmlPath = "/com/ingsw/easytournament/fxml/creazione_modalita/eliminazione_diretta.fxml";
                break;
            default:
                return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent nodoOpzioni = loader.load();

            nodoOpzioni.getStylesheets().add(getClass().getResource("/com/ingsw/easytournament/css/modalita_torneo.css").toExternalForm());

            box_destra.getChildren().setAll(nodoOpzioni);
            AnchorPane.setTopAnchor(nodoOpzioni, 0.0);
            AnchorPane.setBottomAnchor(nodoOpzioni, 0.0);
            AnchorPane.setLeftAnchor(nodoOpzioni, 0.0);
            AnchorPane.setRightAnchor(nodoOpzioni, 0.0);

            Object controller = loader.getController();

            if (controller instanceof ControlloreOpzioni) {
                this.controllerOpzioniCorrente = (ControlloreOpzioni) controller;

                if (idModalita == torneo.getIdModalità()) {
                    this.controllerOpzioniCorrente.caricaDati(torneo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore nel caricamento del pannello opzioni: " + fxmlPath);
        }
    }

    @FXML
    void aggiungiSquadra(ActionEvent event) {
        String campoNomeSquadra = campo_squadra.getText().trim();
        if (campoNomeSquadra.isEmpty()) {
            mostraAlert("Campo nome squadra vuoto!");
            return;
        }

        for(Squadra squadra : elencoSquadre){
            if (squadra.getNome().equals(campoNomeSquadra)){
                mostraAlert("Esiste già una squadra con questo nome!");
                campo_squadra.clear();
                return;
            }
        }
        this.elencoSquadre.add(new Squadra(campoNomeSquadra));
        this.campo_squadra.clear();
    }

    @FXML
    void salvaModifiche(ActionEvent event) {
        if (mostraAlert("Vuoi modificare il torneo?", Alert.AlertType.CONFIRMATION)) {

            String campoNomeTorneo = campo_nome_torneo.getText().trim();
            int modalitaSelezionata = campo_modalita.getSelectionModel().getSelectedIndex();
            LocalDate oggi = LocalDate.now();
            LocalDate dataInserita = campo_data.getValue();

            //logica nome torneo
            if (campoNomeTorneo.isEmpty()) {
                mostraAlert("Campo nome torneo vuoto!");
                return;
            }

            if (!campoNomeTorneo.matches("[a-zA-Z0-9]+")) {
                mostraAlert("Il nome del torneo non può contenere caratteri speciali!");
                return;
            }

            //logica data
            if (dataInserita == null) {
                mostraAlert("Data inserita vuota!");
                return;
            }

            if (dataInserita.isBefore(oggi)) {
                mostraAlert("La data inserita non deve essere precedente alla data odierna!");
                return;
            }

            //logica numero squadre + modalità
            if (campo_modalita.getValue() == null) {
                mostraAlert("Nessuna modalita selezionata!");
                return;
            }

            if (!campo_nome_torneo.getText().trim().equals(torneo.getNome())) {
                boolean esisteGia = HomeModel.torneoEsistente(campo_nome_torneo.getText().trim(), SessioneUtente.getInstance().getUserId());

                if (esisteGia) {
                    mostraAlert("Esiste già un torneo con questo nome!");
                    return;
                }
            }

            int numeroSquadre = elencoSquadre.toArray().length;
            switch (modalitaSelezionata) {
                //girone all'italiana
                case 0: {
                    if (numeroSquadre < 2) {
                        mostraAlert("Il numero delle squadre inserito non è valido per la modalità selezionata");
                        return;
                    }
                    break;
                }

                //eliminazione diretta
                case 1: {
                    boolean èUnaPotenzaDi2 = (numeroSquadre > 0) && ((numeroSquadre & (numeroSquadre - 1)) == 0);
                    if (èUnaPotenzaDi2 == false) {
                        mostraAlert("Il numero delle squadre inserito non è valido per la modalità selezionata");
                        return;
                    }
                    break;
                }
            }
            modificaParametriTorneo();
            this.controllerOpzioniCorrente.salvaDati(torneo);

            if (torneo.getIdModalità() == 1) {
                // Recuperiamo l'oggetto modalità castato
                if (torneo.getModalita() instanceof EliminazioneDiretta) {
                    EliminazioneDiretta ed = (EliminazioneDiretta) torneo.getModalita();

                    // SE la finalina è attiva E le squadre sono 2 o meno -> ERRORE
                    if (ed.isFinalina() && elencoSquadre.size() <= 2) {
                        mostraAlert("Non puoi avere la finalina 3°/4° posto con meno di 4 squadre! Aggiungi squadre o togli la spunta.");
                        return; // Blocca il salvataggio
                    }
                }
            }

            button_salva_modifiche.setDisable(true);
            //salvo nel db
            Task<Boolean> salvaTorneo = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return HomeModel.salvaTorneo(torneo);
                }
            };

            salvaTorneo.setOnSucceeded(e -> {
                boolean salvato = salvaTorneo.getValue();
                if (salvato) {
                    mostraAlert("Torneo modificato con successo!");
                    Stage stage = (Stage) button_salva_modifiche.getScene().getWindow();
                    stage.close();
                } else {
                    button_salva_modifiche.setDisable(false);
                    mostraAlert("Errore durante la modifica del torneo!");
                }
            });

            new Thread(salvaTorneo).start();
        }
    }

    private void modificaParametriTorneo() {
        torneo.setNome(campo_nome_torneo.getText());
        torneo.setData(campo_data.getValue());
        torneo.setIdModalità(campo_modalita.getSelectionModel().getSelectedIndex());
        torneo.setSquadre(elencoSquadre);
    }

    private void mostraAlert(String testoErrore) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Attenzione");
        alert.setGraphic(null);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/com/ingsw/easytournament/css/alert.css");

        alert.setContentText(testoErrore);
        alert.showAndWait();
    }

    private boolean mostraAlert(String testoErrore, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setTitle("Attenzione");
        alert.setGraphic(null);
        alert.setContentText(testoErrore);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/com/ingsw/easytournament/css/alert.css");

        alert.showAndWait();
        if (tipo == Alert.AlertType.CONFIRMATION) {
            return alert.getResult() == ButtonType.OK;
        }
        return true;
    }

}
