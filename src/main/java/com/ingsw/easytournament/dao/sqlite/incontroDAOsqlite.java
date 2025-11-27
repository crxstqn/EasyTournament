package com.ingsw.easytournament.dao.sqlite;

import com.ingsw.easytournament.dao.incontroDAO;

import java.sql.Connection;

public class incontroDAOsqlite implements incontroDAO {
    Connection conn;

    public incontroDAOsqlite(Connection conn) {
        this.conn = conn;
    }
}
