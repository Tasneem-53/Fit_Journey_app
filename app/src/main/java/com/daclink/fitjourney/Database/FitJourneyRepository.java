package com.daclink.fitjourney.Database;

import android.app.Application;


import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;

import java.util.List;

public class FitJourneyRepository {
    private MealsDAO mealsDAO;
    private ExerciseDAO exerciseDAO;

    public FitJourneyRepository(Application application) {
        FitJourneyDatabase db = FitJourneyDatabase.getDatabase(application);
        this.mealsDAO = db.mealsDAO();
        this.exerciseDAO = db.exerciseDAO();
    }

    public void insertMeal(Meals meal) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            mealsDAO.insert(meal);
        });
    }

    public List<Meals> getAllMeals() {
        return mealsDAO.getAllMeals();
    }

    public void insertExercise(Exercise exercise) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDAO.insert(exercise);
        });
    }

    public List<Exercise> getAllExercises() {
        return exerciseDAO.getAllExercises();
    }

    public void deleteAllExercises() {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDAO.deleteAll();
        });
    }

    public List<Meals> getAllMealsOrderedByDate() {
        return mealsDAO.getAllMealsOrderedByDate();
    }

    public void deleteAllMeals() {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            mealsDAO.deleteAll();
        });
    }


}
