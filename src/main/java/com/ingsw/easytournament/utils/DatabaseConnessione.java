package com.ingsw.easytournament.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnessione {
    private static final String DB_posizione = "jdbc:sqlite:database_EasyTournament.db";
    private static Connection conn = null;

    public static Connection get_connessione() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_posizione);
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void chiudiConnessione() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
