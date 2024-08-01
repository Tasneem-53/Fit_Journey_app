package com.daclink.fitjourney.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.fitjourney.Database.entities.Meals;

import java.util.List;

@Dao
public interface MealsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meals... meals);

    @Delete
    void delete(Meals meals);

    @Query("SELECT * FROM " + FitJourneyDatabase.MEALS_TABLE)
    List<Meals> getAllMeals();

    @Query("SELECT * FROM " + FitJourneyDatabase.MEALS_TABLE + " ORDER BY date DESC")
    List<Meals> getAllMealsOrderedByDate();

    @Query("DELETE FROM " + FitJourneyDatabase.MEALS_TABLE)
    void deleteAll();
}
