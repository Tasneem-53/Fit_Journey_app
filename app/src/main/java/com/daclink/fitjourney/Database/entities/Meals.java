package com.daclink.fitjourney.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = FitJourneyDatabase.MEALS_TABLE)
public class Meals {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String meal;
    private double calories;
    private String protein;
    private String fat;
    private String carbs;
    private LocalDate date;
    private int mealId;


    public Meals(String meal, double calories, String protein, String fat, String carbs, LocalDate date, int mealId) {
        this.meal = meal;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.date = date;
        this.mealId = mealId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meals meals = (Meals) o;
        return id == meals.id && Double.compare(calories, meals.calories) == 0 && mealId == meals.mealId && Objects.equals(meal, meals.meal) && Objects.equals(protein, meals.protein) && Objects.equals(fat, meals.fat) && Objects.equals(carbs, meals.carbs) && Objects.equals(date, meals.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, calories, protein, fat, carbs, date, mealId);
    }




}
