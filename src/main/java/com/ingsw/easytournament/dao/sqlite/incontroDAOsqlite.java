package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.incontroDAO;
import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class incontroDAOsqlite implements incontroDAO {
    Connection conn;

    public incontroDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean salvaIncontro(int idTorneo, Map<Integer,List<Incontro>> incontri) {
        String query = "INSERT INTO incontro (id_torneo, squadra_casa, squadra_ospite, giornata, girone) VALUES (?, ?, ?, ?, ?)";
        for (Map.Entry<Integer, List<Incontro>> entry : incontri.entrySet()) {
            System.out.println("Giornata " + entry.getKey() + " - Numero incontri: " + entry.getValue().size());
            for (Incontro incontro : entry.getValue()) {
                System.out.println("  Squadra1 ID: " + incontro.getSquadra1().getId() + " - Squadra2 ID: " + incontro.getSquadra2().getId());
                try (PreparedStatement statement = conn.prepareStatement(query)) {
                    statement.setInt(1, idTorneo);
                    statement.setInt(2, incontro.getSquadra1().getId());
                    statement.setInt(3, incontro.getSquadra2().getId());
                    statement.setInt(4, entry.getKey());
                    statement.setInt(5, incontro.getGruppo());

                    statement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
