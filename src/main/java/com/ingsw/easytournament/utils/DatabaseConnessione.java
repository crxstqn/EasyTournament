package com.ingsw.easytournament.utils;

import com.ingsw.easytournament.dao.sqlite.torneoDAOsqlite;
import com.ingsw.easytournament.dao.sqlite.utenteDAOsqlite;
import com.ingsw.easytournament.dao.torneoDAO;
import com.ingsw.easytournament.dao.utenteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnessione {
    private static final String DB_URL = "jdbc:sqlite:db_EasyTournament.db";
    private static Connection conn = null;
    private static DatabaseConnessione instance = null;

    private DatabaseConnessione() {}

    public static DatabaseConnessione getInstance() {
        if (instance == null) {
            instance = new DatabaseConnessione();
        }
        return instance;
    }

    public Connection getConnessione() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void chiudiConnessione() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public utenteDAO getUtenteDAO() {
        return new utenteDAOsqlite(getConnessione());
    }
    public torneoDAO getTorneoDAO() {
        return new torneoDAOsqlite(getConnessione());
    }
}
