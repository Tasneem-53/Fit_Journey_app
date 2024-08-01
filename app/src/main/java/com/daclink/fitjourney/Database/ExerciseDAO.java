package com.daclink.fitjourney.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.fitjourney.Database.entities.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM " + FitJourneyDatabase.EXERCISE_TABLE + " ORDER BY date DESC")
    List<Exercise> getAllExercises(); //

    @Query("DELETE FROM " + FitJourneyDatabase.EXERCISE_TABLE)
    void deleteAll();
}
