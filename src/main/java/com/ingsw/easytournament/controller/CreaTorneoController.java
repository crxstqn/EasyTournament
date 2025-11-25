package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.controller.modalita.EliminazioneDirettaController;
import com.ingsw.easytournament.controller.modalita.ModalitaController;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CreaTorneoController {

    @FXML
    private Button button_aggiungi;

    @FXML
    private Button button_avanti;

    @FXML
    private DatePicker campo_data;

    @FXML
    private ChoiceBox<String> campo_modalita;

    @FXML
    private TextField campo_nome_torneo;

    @FXML
    private TextField campo_squadra;

    @FXML
    private Label label_data;

    @FXML
    private Label label_gestione_squadre;

    @FXML
    private Label label_modalita;

    @FXML
    private Label label_nome_torneo;

    @FXML
    private ListView<String> list_view;

    private final ObservableList<String> elencoSquadre = FXCollections.observableArrayList();

    public void initialize(){
        campo_modalita.getItems().addAll("Girone all'italiana", "Eliminazione diretta ", "Gironi + Play-off");
        list_view.setItems(elencoSquadre);

        // gestione squadre
        list_view.setCellFactory(param -> new ListCell<String>() {
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
                    String squadraDaEliminare = getItem(); // Recupera il nome della squadra di questa riga
                    elencoSquadre.remove(squadraDaEliminare); // La rimuove dalla lista dati
                    // La ListView si aggiornerà automaticamente!
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    // Se la riga è vuota, non mostrare nulla
                    setText(null);
                    setGraphic(null);
                    // È buona norma resettare lo stile per evitare artefatti grafici
                    setStyle("");
                } else {
                    // Se c'è una squadra, configura la Label e mostra l'HBox personalizzato
                    labelNome.setText(item);
                    setText(null); // Nascondiamo il testo standard della cella
                    setGraphic(rootHBox); // Mostriamo il nostro HBox

                    // Opzionale: forza l'HBox a occupare tutta la larghezza della cella
                    rootHBox.prefWidthProperty().bind(getListView().widthProperty().subtract(20)); // subtract per padding/scroll
                }
            }
        });
    }

    @FXML
    void aggiungiSquadra(ActionEvent event) {
        String campoNomeSquadra = campo_squadra.getText();
        if (campoNomeSquadra.isEmpty()) {
            mostraAlert("Campo nome squadra vuoto!");
            return;
        }

        for(String squadra : elencoSquadre){
            if (squadra.equals(campoNomeSquadra)){
                mostraAlert("Esiste già una squadra con questo nome!");
                campo_squadra.clear();
                return;
            }
        }

        elencoSquadre.add(campoNomeSquadra);
        campo_squadra.clear();
    }

    @FXML
    void scorriAvanti(ActionEvent event) {
        String  campoNomeTorneo = campo_nome_torneo.getText();
        int modalitaSelezionata = campo_modalita.getSelectionModel().getSelectedIndex();
        LocalDate oggi = LocalDate.now();
        LocalDate dataInserita = campo_data.getValue();

        //logica nome torneo
        if (campoNomeTorneo.isEmpty()) {
            mostraAlert("Campo nome torneo vuoto!");
            return;
        }

        if (!campoNomeTorneo.matches("[a-zA-Z0-9]+")){
            mostraAlert("Il nome del torneo non può contenere caratteri speciali!");
            return;
        }

        //logica data
        if (dataInserita == null){
            mostraAlert("Data inserita vuota!");
            return;
        }

        if (dataInserita.isBefore(oggi)){
            mostraAlert("La data inserita non deve essere precedente alla data odierna!");
            return;
        }

        //logica numero squadre + modalità
        if (campo_modalita.getValue() == null) {
            mostraAlert("Nessuna modalita selezionata!");
            return;
        }

        int numeroSquadre = elencoSquadre.toArray().length;
        switch (modalitaSelezionata) {
            //girone all'italiana
            case 0: {
                if (numeroSquadre < 2) {
                    mostraAlert("Il numero delle squadre inserito non è valido per la modalità selezionata");
                    return;
                }
                cambiaSchermata("/com/ingsw/easytournament/fxml/modalita/girone_all_italiana.fxml","/com/ingsw/easytournament/css/modalita/girone_all_italiana.css");
                break;
            }

            //eliminazione diretta
            case 1:{
                boolean èUnaPotenzaDi2 = (numeroSquadre > 0) && ((numeroSquadre & (numeroSquadre - 1)) == 0);
                if (èUnaPotenzaDi2 == false){
                    mostraAlert("Il numero delle squadre inserito non è valido per la modalità selezionata");
                    return;
                }
                cambiaSchermata("/com/ingsw/easytournament/fxml/modalita/eliminazione_diretta.fxml","/com/ingsw/easytournament/css/modalita/eliminazione_diretta.css");
                break;
            }

            //girone + eliminazione diretta
            case 2:
            {
                if (numeroSquadre<4){
                    mostraAlert("Il numero delle squadre inserito non è valido per la modalità selezionata");
                    return;
                }
                cambiaSchermata("/com/ingsw/easytournament/fxml/modalita/gironi_playoff.fxml","/com/ingsw/easytournament/css/modalita/gironi_playoff.css");
                break;
            }
        }
    }

    private Torneo creaTorneo(){
        String nomeTorneo = campo_nome_torneo.getText();
        LocalDate date = campo_data.getValue();
        int idModalita = campo_modalita.getSelectionModel().getSelectedIndex();
        List<String> squadre = elencoSquadre.stream().toList();
        int idUtente = SessioneUtente.getInstance().getUserId();

        return new Torneo(idModalita,idUtente,nomeTorneo,date,squadre);
    }

    private void cambiaSchermata(String fxml, String css) {
        try {
            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            ModalitaController controller = loader.getController();
            controller.setTorneo(creaTorneo());


            Scene scene = button_avanti.getScene();
            Stage stage = (javafx.stage.Stage) scene.getWindow();

            scene.setRoot(root);

            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(css).toExternalForm());

            stage.sizeToScene();
            stage.centerOnScreen();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
