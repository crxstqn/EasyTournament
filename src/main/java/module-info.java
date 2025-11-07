module com.ingsw.easytournament {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ingsw.easytournament to javafx.fxml;
    exports com.ingsw.easytournament;
    exports com.ingsw.easytournament.controller;
    opens com.ingsw.easytournament.controller to javafx.fxml;
}