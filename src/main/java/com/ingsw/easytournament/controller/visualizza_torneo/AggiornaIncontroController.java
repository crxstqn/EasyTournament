package com.ingsw.easytournament.controller.visualizza_torneo;

import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.SessioneAggiornamentoIncontro;
import com.ingsw.easytournament.model.VisualizzazioneModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AggiornaIncontroController {
    
    @FXML
    private Button button_raggiorna_risultato;

    @FXML
    private Label label_squadra2;

    @FXML
    private Label label_squadra_1;

    @FXML
    private Label label_titolo;

    @FXML
    private Spinner<Integer> spinner_squadra2;

    @FXML
    private Spinner<Integer> spinner_squadra_1;
    
    private Incontro incontro;
    
    public void initialize(){
        this.incontro = SessioneAggiornamentoIncontro.getInstance().getIncontro();

        //aggiorno le label squadre
        label_squadra_1.setText(incontro.getSquadra1().getNome());
        label_squadra2.setText(incontro.getSquadra2().getNome());
        
        //aggiorno gli spinner
        int punteggioDefaultSquadra1 = 0 ;
        int punteggioDefaultSquadra2 = 0 ;
        int punteggioSquadra1 = incontro.getPunteggioSquadra1();
        int punteggioSquadra2 = incontro.getPunteggioSquadra2();
        if (punteggioSquadra1 != -1) punteggioDefaultSquadra1 = punteggioSquadra1;
        if (punteggioSquadra2 != -1) punteggioDefaultSquadra2 = punteggioSquadra2;

        spinner_squadra_1.setEditable(true);
        spinner_squadra2.setEditable(true);
        spinner_squadra_1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, punteggioDefaultSquadra1 ));
        spinner_squadra2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, punteggioDefaultSquadra2 ));
    }
    
    @FXML
    void aggiorna_risultato(ActionEvent event) {
        incontro.setPunteggioSquadra1(spinner_squadra_1.getValue());
        incontro.setPunteggioSquadra2(spinner_squadra2.getValue());
        Task<Boolean> aggiornaIncontro = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return VisualizzazioneModel.getInstance().aggiornaIncontro(incontro);
            }
        };
        
        aggiornaIncontro.setOnSucceeded(e -> {
            boolean aggiornato = aggiornaIncontro.getValue();
            if (aggiornato){
                mostraAlert("Risultato aggiornato con successo!");
            }
            else{
                mostraAlert("Errore durante l'aggiornamento del risultato!");
            }
            Stage stage = ((Stage)button_raggiorna_risultato.getScene().getWindow());
            stage.close();
        });
        
        new Thread(aggiornaIncontro).start();
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
