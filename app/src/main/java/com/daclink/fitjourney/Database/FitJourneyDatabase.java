package com.daclink.fitjourney.Database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
public abstract class FitJourneyDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "FitJourney Database";

    public static final String EXERCISE_TABLE = "Exercise";

    private static volatile FitJourneyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FitJourneyDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (FitJourneyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FitJourneyDatabase.class,
                                        DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract FitJourneyDAO fitJourneyDAO();

}