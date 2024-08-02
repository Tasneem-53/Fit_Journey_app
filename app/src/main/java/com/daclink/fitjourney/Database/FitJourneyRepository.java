package com.daclink.fitjourney.Database;

import android.app.Application;


import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FitJourneyRepository {
    private MealsDAO mealsDAO;
    private ExerciseDAO exerciseDAO;
    private UserDAO userDao;

    public FitJourneyRepository(Application application) {
        FitJourneyDatabase db = FitJourneyDatabase.getDatabase(application);
        this.mealsDAO = db.mealsDAO();
        this.exerciseDAO = db.exerciseDAO();
        this.userDao = db.userDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
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

    public void deleteUser(final int username, final RepositoryCallback callback) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            int rowsDeleted = userDao.deleteUserByUsername(username);
            callback.onComplete(rowsDeleted > 0);
        });
    }

    public interface RepositoryCallback {
        void onComplete(boolean success);
    }
}
