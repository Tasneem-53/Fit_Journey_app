package com.daclink.fitjourney.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = FitJourneyDatabase.EXERCISE_TABLE)
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int Id;


    private int sets;
    private int reps;
    private double weight;
    private LocalDate date;

    public Exercise(int sets, int reps, double weight) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Id == exercise.Id && sets == exercise.sets && reps == exercise.reps && Double.compare(weight, exercise.weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, sets, reps, weight);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

