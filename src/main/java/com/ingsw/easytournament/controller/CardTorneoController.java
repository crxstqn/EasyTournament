package com.ingsw.easytournament.controller;

import com.ingsw.easytournament.model.HomeModel;
import com.ingsw.easytournament.model.SessioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Optional;

public class CardTorneoController {

    @FXML
    private Button button_elimina;

    @FXML
    private Button button_modifica;

    @FXML
    private Label label_data;

    @FXML
    private Label label_nome_torneo;

    @FXML
    private VBox vbox_principale;

    private Torneo torneo;
    private HomeController homeController;

    public void initialize(){
        button_elimina.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent::consume);
        button_modifica.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent::consume);
    }

    @FXML
    void eliminaTorneo(ActionEvent evento) {
        Optional<ButtonType> risultatoConferma = chiediConfermaEliminazione();

        if (risultatoConferma.isPresent() && risultatoConferma.get() == ButtonType.OK) {
            vbox_principale.setDisable(true);

            //animazione
            FadeTransition effettoFade = new FadeTransition(Duration.millis(500), vbox_principale);
            effettoFade.setFromValue(1.0);
            effettoFade.setToValue(0.0);

            // chiamata al db
            Task<Boolean> taskEliminazioneTorneo = new Task<>() {
                @Override
                protected Boolean call() {
                    return HomeModel.eliminaTorneo(torneo.getId());
                }
            };

            taskEliminazioneTorneo.setOnSucceeded(e -> {
                homeController.caricaTornei();
                System.out.println("Torneo eliminato e finestra chiusa");
            });


            effettoFade.setOnFinished(event -> {
                new Thread(taskEliminazioneTorneo).start();
            });
            //
            effettoFade.play();
        }
    }

    @FXML
    void visualizzaTorneo(MouseEvent event) {
        String fxmlDestinazione = "";
        String cssDestinazione = "/com/ingsw/easytournament/css/visualizzazione_torneo.css";

        switch (this.torneo.getIdModalità()){
            case 0: fxmlDestinazione = "/com/ingsw/easytournament/fxml/visualizza_torneo/girone_all_italiana.fxml"; break;
            case 1: fxmlDestinazione = "/com/ingsw/easytournament/fxml/visualizza_torneo/eliminazione_diretta.fxml"; break;
        }

        SessioneTorneo.getInstance().eliminaTorneo();
        SessioneTorneo.getInstance().setBozzaTorneo(this.torneo);

        SceneChanger.getInstance().changeScene(fxmlDestinazione,cssDestinazione,true);
    }

    @FXML
    void modificaTorneo(ActionEvent event) {
        if (LocalDate.now().isBefore(torneo.getData())) {
            SessioneTorneo.getInstance().eliminaTorneo();
            SessioneTorneo.getInstance().setBozzaTorneo(torneo);
            SceneChanger.getInstance().createModalityStage("/com/ingsw/easytournament/fxml/modifica_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_modifica_torneo.css", "Modifica Torneo");
            label_nome_torneo.setText(torneo.getNome());
            label_data.setText(torneo.getData().toString());
        }
        else {
            mostraAlert("Non puoi modificare un torneo già iniziato");
        }
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
        label_nome_torneo.setText(torneo.getNome());
        label_data.setText(torneo.getData().toString());
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private Optional<ButtonType> chiediConfermaEliminazione(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/com/ingsw/easytournament/css/alert.css");
        dialogPane.getStyleClass().add("danger-alert");

        alert.setTitle("Conferma Eliminazione");
        alert.setHeaderText("Stai per eliminare il torneo: " + torneo.getNome());
        alert.setContentText("Sei sicuro? Questa azione è irreversibile");
        return alert.showAndWait();
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
}
