package com.ingsw.easytournament.utils;

import org.mindrot.jbcrypt.BCrypt;


public class Pass {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
