package com.daclink.fitjourney.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

@Entity(tableName = FitJourneyDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private boolean isAdmin;


    // Constructors
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
