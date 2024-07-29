package com.daclink.fitjourney.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

import java.util.Objects;

@Entity(tableName = FitJourneyDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int UserId;


    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return UserId == user.UserId && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId, username, email, password);
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

