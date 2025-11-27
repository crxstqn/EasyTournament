package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.modalitaDAO;

import java.sql.Connection;

public class modalitaDAOsqlite implements modalitaDAO {
    Connection conn;

    public modalitaDAOsqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean creaModalita(String nome, int puntiVittoria, int puntiPareggio, int puntiSconfitta, boolean andataEritorno, boolean finalina) {
        return false;
    }
}
