package com.daclink.fitjourney.Database;

import android.app.Application;
import android.util.Log;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FitJourneyRepository {
    private FitJourneyDAO fitJourneyDAO;
    private ArrayList<Exercise> allLogs;

    public FitJourneyRepository(Application application){
        FitJourneyDatabase db = FitJourneyDatabase.getDatabase(application);
        this.fitJourneyDAO = db.fitJourneyDAO();
        this.allLogs = this.fitJourneyDAO.getAllRecords();
    }

    public ArrayList<Exercise> getAllLogs(){
        Future<ArrayList<Exercise>> future = FitJourneyDatabase.databaseWriteExecutor.submit(

                new Callable<ArrayList<Exercise>>() {
                    @Override
                    public ArrayList<Exercise> call() throws Exception {
                        return fitJourneyDAO.getAllRecords();
                    }
                }

        );

        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all ExerciseLogs in the repository");
        }
        return null;
    }

public void insertExercise(Exercise exercise){
        FitJourneyDatabase.databaseWriteExecutor.execute(() ->
        {
            fitJourneyDAO.insert(exercise);
        });
}

}
