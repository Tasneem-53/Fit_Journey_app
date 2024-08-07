package com.daclink.fitjourney.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.User;

import java.util.List;

@Dao
public interface FitJourneyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exercise exercise);

    @Query("SELECT * FROM " + FitJourneyDatabase.EXERCISE_TABLE)
    LiveData<List<Exercise>> getAllRecordsLive();

    @Update
    void updateUser(User user);
}
