package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model;

/**
 * Created by M. Satria Wibawa on 01/04/2018.
 */

public class User {
    String username;
    String email;

    public User(){}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
