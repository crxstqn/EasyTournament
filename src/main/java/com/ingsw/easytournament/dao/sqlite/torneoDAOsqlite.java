package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.torneoDAO;
import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;
import com.ingsw.easytournament.utils.DatabaseConnessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class torneoDAOsqlite implements torneoDAO {
    Connection conn;

    public torneoDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Torneo> getTorneiByUtente(int idUtente) {
        List<Torneo> tornei = new ArrayList<>();
        String query = "SELECT * FROM torneo WHERE utente = ?";

        try(PreparedStatement statement = conn.prepareStatement(query) ){
            statement.setInt(1,idUtente);
            ResultSet risultato = statement.executeQuery();
            while(risultato.next()){
                int id = risultato.getInt(1);
                String nome = risultato.getString(2);
                String dataString = risultato.getString(3);
                LocalDate data = LocalDate.parse(dataString);
                int modalita = risultato.getInt(4);
                int id_utente = risultato.getInt(5);
                String confTorneo = risultato.getString(6);
                Torneo nuovoTorneo = new Torneo(data,id,id_utente,nome,modalita, confTorneo);

                List<Squadra> squadre = DatabaseConnessione.getInstance().getSquadraDAO().getSquadreDaTorneo(nuovoTorneo.getId());
                nuovoTorneo.setSquadre(squadre);

                Map<Integer,List<Incontro>> incontri = DatabaseConnessione.getInstance().getIncontroDAO().getIncontriDaTorneo(nuovoTorneo);
                nuovoTorneo.setIncontri(incontri);

                tornei.add(nuovoTorneo);
            }
            return tornei;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminaTorneo(int idTorneo) {
        String query = "DELETE FROM torneo WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, idTorneo);
            int righe_modificate = statement.executeUpdate();
            return righe_modificate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean creaTorneo(Torneo torneo) {
        String query = "INSERT INTO torneo (nome, data, modalita, utente, configurazione) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, torneo.getNome());
            statement.setString(2, torneo.getData().toString());
            statement.setInt(3, torneo.getIdModalità());
            statement.setInt(4, torneo.getId_utente());
            statement.setString(5, torneo.getModalita().getConfigurazione());

            int esecuzione = statement.executeUpdate();

            if (esecuzione > 0){
                try(ResultSet rs = statement.getGeneratedKeys()){
                    if (rs.next()) {
                        torneo.setId(rs.getInt(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DatabaseConnessione.getInstance().getSquadraDAO().salvaSquadra(torneo.getId(), torneo.getSquadre());
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean modificaTorneo(Torneo torneo){
        return false;
    }

//    public boolean torneoEsistente(String idTorneo){
//        String query = "SELECT * FROM torneo WHERE id = ?";
//
//        try {PreparedStatement statement = conn.prepareStatement(query);
//    }
}
