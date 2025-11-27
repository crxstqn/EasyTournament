package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.squadraDAO;

import java.sql.Connection;

public class squadraDAOsqlite implements squadraDAO {
    Connection conn;

    public squadraDAOsqlite(Connection conn) {
        this.conn = conn;
    }

}
