package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.torneoDAO;
import com.ingsw.easytournament.model.Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                tornei.add(new Torneo(data,id,id_utente,nome,modalita));
            }
            return tornei;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
