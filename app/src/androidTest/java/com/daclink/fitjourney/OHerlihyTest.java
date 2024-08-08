// Liam O'Herlihy

package com.daclink.fitjourney;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;

public class OHerlihyTest {

    Exercise testExercise1 = null;
    Exercise testExercise2 = null;
    Meals testMeal1 = null;
    Meals testMeal2 = null;
    String exerciseName1 = "Running";
    String exerciseName2 = "Swimming";
    double exerciseDuration1 = 30.0;
    double exerciseDuration2 = 45.0;
    String mealName1 = "Breakfast";
    String mealName2 = "Lunch";
    double mealCalories1 = 500.0;
    double mealCalories2 = 800.0;
    LocalDate date = LocalDate.now();
    List<Exercise> exerciseList = null;
    List<Meals> mealList = null;

    @Before
    public void setup(){
        testExercise1 = new Exercise(exerciseName1, exerciseDuration1, date);
        testExercise2 = new Exercise(exerciseName2, exerciseDuration2, date);
        testMeal1 = new Meals(mealName1, mealCalories1, date);
        testMeal2 = new Meals(mealName2, mealCalories2, date);
        exerciseList = new ArrayList<>();
        exerciseList.add(testExercise1);
        exerciseList.add(testExercise2);
        mealList = new ArrayList<>();
        mealList.add(testMeal1);
        mealList.add(testMeal2);
    }

    @After
    public void tearDown(){
        testExercise1 = null;
        testExercise2 = null;
        testMeal1 = null;
        testMeal2 = null;
        exerciseList = null;
        mealList = null;
    }

    @Test
    public void TestingExercise(){
        // Test Exercise attributes
        assertEquals(exerciseName1, testExercise1.getName());
        assertEquals(exerciseName2, testExercise2.getName());
        assertNotEquals(exerciseName2, testExercise1.getName());
        assertNotEquals(exerciseName1, testExercise2.getName());

        assertEquals(exerciseDuration1, testExercise1.getDuration(), 0);
        assertEquals(exerciseDuration2, testExercise2.getDuration(), 0);
        assertNotEquals(exerciseDuration2, testExercise1.getDuration(), 0);
        assertNotEquals(exerciseDuration1, testExercise2.getDuration(), 0);
    }

    @Test
    public void TestingMeals(){
        // Test Meal attributes
        assertEquals(mealName1, testMeal1.getMeal());
        assertEquals(mealName2, testMeal2.getMeal());
        assertNotEquals(mealName2, testMeal1.getMeal());
        assertNotEquals(mealName1, testMeal2.getMeal());

        assertEquals(mealCalories1, testMeal1.getCalories(), 0);
        assertEquals(mealCalories2, testMeal2.getCalories(), 0);
        assertNotEquals(mealCalories2, testMeal1.getCalories(), 0);
        assertNotEquals(mealCalories1, testMeal2.getCalories(), 0);
    }

    @Test
    public void TestingProgress(){
        // Test progress calculations
        double totalWeightLost = 0;
        for (Exercise exercise : exerciseList) {
            totalWeightLost += exercise.getDuration() * 8 / 3500; // Assuming 8 calories burned per minute
        }

        double totalWeightGained = 0;
        for (Meals meal : mealList) {
            totalWeightGained += meal.getCalories() / 3500; // Assuming 3500 calories = 1 pound
        }

        double expectedWeightLost = (exerciseDuration1 + exerciseDuration2) * 8 / 3500;
        double expectedWeightGained = (mealCalories1 + mealCalories2) / 3500;

        assertEquals(expectedWeightLost, totalWeightLost, 0);
        assertEquals(expectedWeightGained, totalWeightGained, 0);
        assertNotEquals(expectedWeightLost, totalWeightGained, 0); // Ensure they're not incorrectly equal
    }
}
