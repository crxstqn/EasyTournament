package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.squadraDAO;
import com.ingsw.easytournament.model.Squadra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class squadraDAOsqlite implements squadraDAO {
    Connection conn;

    public squadraDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvaSquadra(int idTorneo, List<Squadra> squadra) {
        String query = "INSERT INTO squadra (nome, id_torneo) VALUES (?, ?)";
        for (Squadra s : squadra) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, s.getNome());
                statement.setInt(2, idTorneo);

                int esecuzione = statement.executeUpdate();

                if (esecuzione > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            s.setId(resultSet.getInt(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }catch (SQLException e){
                    e.printStackTrace();
            }
        }
    }

    @Override
    public List<Squadra> getSquadreDaTorneo(int id_torneo) {
        String query = "SELECT * FROM squadra WHERE id_torneo = ?";
        List<Squadra> squadre = new ArrayList<>();

            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, id_torneo);
                ResultSet risultato = statement.executeQuery();
                while (risultato.next()){
                    int id_squadra = risultato.getInt(1);
                    String nome = risultato.getString(2);
                    squadre.add(new Squadra(nome,id_squadra,id_torneo));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return squadre;
    }
}
