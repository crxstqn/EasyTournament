package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.incontroDAO;
import com.ingsw.easytournament.model.Incontro;
import com.ingsw.easytournament.model.Squadra;
import com.ingsw.easytournament.model.Torneo;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class incontroDAOsqlite implements incontroDAO {
    Connection conn;

    public incontroDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean salvaIncontro(int idTorneo, Map<Integer, List<Incontro>> incontri) {
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

    @Override
    public boolean salvaSingoloIncontro(int idTorneo, Pair<Integer, Incontro> incontroFinalina) {
        String query = "INSERT INTO incontro (id_torneo, squadra_casa, squadra_ospite, giornata, girone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idTorneo);
            statement.setInt(2, incontroFinalina.getValue().getSquadra1().getId());
            statement.setInt(3, incontroFinalina.getValue().getSquadra2().getId());
            statement.setInt(4, incontroFinalina.getKey());
            statement.setInt(5, incontroFinalina.getValue().getGruppo());

            int righeModificate = statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int nuovoId = generatedKeys.getInt(1);
                    incontroFinalina.getValue().setId(nuovoId);
                }
            }
            return righeModificate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public Map<Integer, List<Incontro>> getIncontriDaTorneo(Torneo torneo) {
        String query = "SELECT * FROM incontro WHERE id_torneo = ?";
        Map<Integer, List<Incontro>> incontri = new HashMap<>();
        //creo una mappa id_squadra : squadra per evitare di dover scorrere tutte le squadre
        Map<Integer, Squadra> squadraMap = torneo.getSquadre().stream().collect(java.util.stream.Collectors.toMap(Squadra::getId, s -> s));

        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1,torneo.getId());
            ResultSet risultato = statement.executeQuery();

            while (risultato.next()){
                int id_incontro = risultato.getInt(1);
                int id_squadra1 = risultato.getInt(3);
                int id_squadra2 = risultato.getInt(4);
                Squadra s1 = squadraMap.get(id_squadra1);
                Squadra s2 = squadraMap.get(id_squadra2);
                int punteggioSquadra1 = risultato.getInt(5);
                int punteggioSquadra2 = risultato.getInt(6);
                int giornata = risultato.getInt(7);
                int gruppo = risultato.getInt(8);

                incontri.putIfAbsent(giornata, new ArrayList<>());
                incontri.get(giornata).add(new Incontro(torneo.getId(), gruppo,id_incontro,punteggioSquadra1,punteggioSquadra2,s1,s2));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incontri;
    }

    @Override
    public boolean aggiornaIncontro(Incontro incontro) {
        String query = "UPDATE incontro SET punteggio_casa = ?, punteggio_ospite = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, incontro.getPunteggioSquadra1());
            statement.setInt(2, incontro.getPunteggioSquadra2());
            statement.setInt(3, incontro.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Errore aggiornamento incontro");
        return false;
    }

    @Override
    public boolean salvaNuoviIncontri(int turno, List<Incontro> nuoviIncontri) {
        String query = "INSERT INTO incontro (id_torneo, squadra_casa, squadra_ospite, giornata, girone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            for (Incontro incontro : nuoviIncontri) {
                statement.setInt(1, incontro.getIdTorneo());
                statement.setInt(2, incontro.getSquadra1().getId());
                statement.setInt(3, incontro.getSquadra2().getId());
                statement.setInt(4, turno);
                statement.setInt(5, incontro.getGruppo());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int nuovoId = generatedKeys.getInt(1);
                        incontro.setId(nuovoId);
                    }
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }
}
