package com.ingsw.easytournament.controller.visualizza_torneo;

import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.SessioneCreazioneTorneo;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
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
    private TableView<?> table_view_classifica;

    private Torneo torneo;

    public void initialize(){
        this.torneo = SessioneCreazioneTorneo.getInstance().getBozzaTorneo();
        this.label_titolo.setText(torneo.getNome());
        boolean torneoIniziato = (this.torneo.getData().isBefore(LocalDate.now()) || this.torneo.getData().isEqual(LocalDate.now()));
        if (torneoIniziato){
            this.label_stato.setText("Stato: In corso");
        }
        else {
            this.label_stato.setText("Data di inizio: " + this.torneo.getData().toString());
        }
        caricaIncontri();
    }

    private void caricaIncontri(){
        Map<Integer, List<Incontro>> incontri = torneo.getIncontri();
        System.out.println(incontri.size());

//        for (Map.Entry<Integer, List<Incontro>> giornata : incontri.entrySet()) {
//            selettore_giornate.getItems().add("Giornata " + giornata);
//            selettore_giornate.getSelectionModel().selectFirst();
//        }
    }

    @FXML
    void tornaHome(ActionEvent event) {
        SceneChanger.getInstance().changeScene("/com/ingsw/easytournament/fxml/home.fxml", "/com/ingsw/easytournament/css/home.css",true);
    }

}
