package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.utenteDAO;
import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.Pass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class utenteDAOsqlite implements utenteDAO {
    Connection conn;

    public utenteDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean autenticazione(String username, String password) {
        String query = "SELECT id, password FROM utente WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet risultato = stmt.executeQuery();

            if (risultato.next()) {
                String hash_database = risultato.getString("password");
                boolean esito = Pass.verifyPassword(password, hash_database);

                if (esito) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean usernameEsistente(String username) {
        String query = "SELECT COUNT(*) FROM utente WHERE username = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet risultato = stmt.executeQuery();

            if (risultato.next()) {
                return risultato.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean registrazioneUtente(String nome, String username, String password) {
        String query = "INSERT INTO utente (nome, username, password) VALUES (?, ?, ?)";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            stmt.setString(2, username);
            stmt.setString(3, Pass.hashPassword(password));

            int righe_modificate = stmt.executeUpdate();
            return righe_modificate > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getUtenteId(String username) {
        String query = "SELECT id FROM utente WHERE username = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username.toLowerCase());
            ResultSet risultato = stmt.executeQuery();

            if (risultato.next()) {
                return risultato.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getUtenteNome(int id) {
        String query = "SELECT nome FROM utente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet risultato = stmt.executeQuery();
            if (risultato.next()) {
                return risultato.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
