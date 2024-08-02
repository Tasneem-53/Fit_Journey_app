package com.daclink.fitjourney.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = FitJourneyDatabase.MEALS_TABLE)
public class Meals {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String meal;
    private double calories;
    private LocalDate date;

    // Default constructor required by Room
    public Meals() {
    }

    // Parameterized constructor
    public Meals(String meal, double calories, LocalDate date) {
        this.meal = meal;
        this.calories = calories;
        this.date = date;
    }

    // Getter and Setters
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

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
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
        return id == meals.id && Double.compare(meals.calories, calories) == 0 && meal.equals(meals.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, calories, date);
    }
}
