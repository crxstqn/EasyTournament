package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.Pass;

import java.sql.*;

public class LoginRegistrazioneModel {
    public static boolean autenticazione(String username, String password) {
        String query = "SELECT id, password FROM utente WHERE email = ?";

        try (Connection conn = DatabaseConnessione.get_connessione();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username.toLowerCase());
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

    public static boolean usernameEsistente(String username) {
        String query = "SELECT COUNT(*) FROM utente WHERE username = ?";

        try (Connection conn = DatabaseConnessione.get_connessione();
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

    public static boolean registrazioneUtente(String nome, String username, String password) {
        String query = "INSERT INTO utente (nome, username, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnessione.get_connessione();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            stmt.setString(2, username.trim().toLowerCase());
            stmt.setString(3, Pass.hashPassword(password));

            int righe_modificate = stmt.executeUpdate();
            return righe_modificate > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

//    public static int getUtenteId(String username){
//        String query = "SELECT id FROM utente WHERE username = ?";
//
//        try (Connection conn = DatabaseConnessione.get_connessione();
//             PreparedStatement stmt = conn.prepareStatement(query)){
//
//            stmt.setString(1, username.toLowerCase());
//            ResultSet risultato= stmt.executeQuery();
//
//            if (risultato.next()){
//                return risultato.getInt("id");
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return -1;
//    }
}
