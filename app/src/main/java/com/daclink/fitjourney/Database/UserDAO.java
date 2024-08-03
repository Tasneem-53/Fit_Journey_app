package com.daclink.fitjourney.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import com.daclink.fitjourney.Database.entities.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User WHERE username = :userId")
    int deleteUserByUsername(int userId);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + FitJourneyDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query(" DELETE FROM " + FitJourneyDatabase.USER_TABLE)
    void deleteAll();
}


