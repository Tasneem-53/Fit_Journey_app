package com.daclink.fitjourney.Database;

import android.app.Application;
import android.util.Log;


import androidx.lifecycle.LiveData;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.MainActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FitJourneyRepository {
    private final MealsDAO mealsDAO;
    private final ExerciseDAO exerciseDAO;
    private final UserDAO userDAO;




    private static FitJourneyRepository repository;

    public FitJourneyRepository(Application application) {
        FitJourneyDatabase db = FitJourneyDatabase.getDatabase(application);
        this.mealsDAO = db.mealsDAO();
        this.exerciseDAO = db.exerciseDAO();
        this.userDAO = db.userDAO();
        this.exerciseDAO.getAllExercises();
        this.mealsDAO.getAllMeals();



    }

    public static FitJourneyRepository getRepository(Application application){
        if(repository !=null){
            return repository;
        }
        Future<FitJourneyRepository> future = FitJourneyDatabase.databaseWriteExecutor.submit(
                new Callable<FitJourneyRepository>() {
                    @Override
                    public FitJourneyRepository call() throws Exception {
                        return  new FitJourneyRepository(application);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error");
        }
        return null;
    }


    public User getUserName(String username) {
        Log.i(MainActivity.TAG, "GETUSERNAME " + username);
        Future<User> future = FitJourneyDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDAO.getUserByUserName(username);
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting user by username in Repository");
        }
        return null;
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
            int rowsDeleted = userDAO.deleteUserByUserId(username);
            callback.onComplete(rowsDeleted > 0);
        });
    }

    // Get a user by username
    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    // Update user password
    public void updateUser(User user) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.update(user);
        });
    }

    // Update password by username
    public void updatePassword(String username, String newPassword) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> userDAO.updatePassword(username, newPassword));
    }



    public interface RepositoryCallback {
        void onComplete(boolean success);
    }

}
