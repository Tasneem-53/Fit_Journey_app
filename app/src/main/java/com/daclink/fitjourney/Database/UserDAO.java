package com.daclink.fitjourney.Database;

import androidx.lifecycle.LiveData;
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

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User WHERE id = :userId")
    int deleteUserByUserId(int userId);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUserByUserName(String username);

    @Query("SELECT * FROM " + FitJourneyDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query(" DELETE FROM " + FitJourneyDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);


    @Query("SELECT * FROM User WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("UPDATE User SET password = :password WHERE username = :username")
    void updatePassword(String username, String password);
}


