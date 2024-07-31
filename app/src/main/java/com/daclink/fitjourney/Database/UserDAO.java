package com.daclink.fitjourney.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import com.daclink.fitjourney.Database.entities.User;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();
}


