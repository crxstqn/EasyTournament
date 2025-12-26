package com.ingsw.easytournament.controller.modalita;

import com.ingsw.easytournament.model.*;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EliminazioneDirettaController implements ControlloreOpzioni {

    @FXML
    private Button button_avanti;

    @FXML
    private Button button_indietro;

    @FXML
    private Label label_titolo;

    @FXML
    private CheckBox scelta_finalina;

    private Torneo torneo;

    private Modalita bozzaModalita;

    public void initialize() {
        torneo = SessioneTorneo.getInstance().getBozzaTorneo();
        bozzaModalita = torneo.getModalita();
        scelta_finalina.setSelected(((EliminazioneDiretta) bozzaModalita).isFinalina());
    }
    @FXML
    void mostraRiepilogo(ActionEvent event) {
        boolean finalina = scelta_finalina.isSelected();
        if (finalina && torneo.getSquadre().size() <=2){
            mostraAlert("Non hai inserito un numero di squadre sufficiente per avere una finalina!");
            return;
        }

        //copiamo
        EliminazioneDiretta castModalita =  (EliminazioneDiretta) bozzaModalita;
        castModalita.setFinalina(finalina);
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/riepilogo_creazione.fxml", "/com/ingsw/easytournament/css/riepilogo_creazione.css", button_avanti.getScene());
    }

    @Override
    public void caricaDati(Torneo torneo) {
        //nascondo i pulsanti perché il metodo è utilizzato solo in modifica
        if (button_avanti != null) button_avanti.setVisible(false);
        if (button_indietro != null) button_indietro.setVisible(false);

        // Carica i dati dal torneo esistente
        if (torneo.getModalita() instanceof EliminazioneDiretta) {
            EliminazioneDiretta ed = (EliminazioneDiretta) torneo.getModalita();
            scelta_finalina.setSelected(ed.isFinalina());
        }
    }

    @Override
    public void salvaDati(Torneo torneo) {
        if (torneo.getModalita() instanceof EliminazioneDiretta) {
            EliminazioneDiretta ed = (EliminazioneDiretta) torneo.getModalita();
            ed.setFinalina(scelta_finalina.isSelected());
        }
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneChanger.getInstance().changeModalityScene("/com/ingsw/easytournament/fxml/aggiungi_torneo.fxml", "/com/ingsw/easytournament/css/aggiungi_modifica_torneo.css", button_indietro.getScene());
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
