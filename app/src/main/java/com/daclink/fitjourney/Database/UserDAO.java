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
import com.daclink.fitjourney.MainActivity;

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
    User getUserByUserName(String username);

    @Query("SELECT * FROM " + FitJourneyDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query(" DELETE FROM " + FitJourneyDatabase.USER_TABLE)
    void deleteAll();

    //we have a static reference to our database table
    //what is defined in out database.java
    @Query(" SELECT * FROM " + FitJourneyDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUser(String username);

    @Query(" SELECT * FROM " + FitJourneyDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

}


