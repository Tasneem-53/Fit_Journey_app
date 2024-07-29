package com.daclink.fitjourney.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.fitjourney.Database.entities.User;

import java.util.ArrayList;

@Dao
public interface FitJourneyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("Select * from " + FitJourneyDatabase.USER_TABLE)
    ArrayList<User> getAllRecords();
}
