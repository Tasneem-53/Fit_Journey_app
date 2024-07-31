package com.daclink.fitjourney.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FitJourneyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exercise exercise);

    @Query("Select * from " + FitJourneyDatabase.EXERCISE_TABLE)
    ArrayList<Exercise> getAllRecords();

}
