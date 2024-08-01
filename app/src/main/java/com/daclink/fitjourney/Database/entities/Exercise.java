package com.daclink.fitjourney.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.fitjourney.Database.FitJourneyDatabase;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = FitJourneyDatabase.EXERCISE_TABLE)
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private double duration; // Duration in minutes
    private LocalDate date;

    // Default constructor required by Room
    public Exercise() {
    }

    // Parameterized constructor
    public Exercise(String name, double duration, LocalDate date) {
        this.name = name;
        this.duration = duration;
        this.date = date;
    }

    // Getter and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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
        Exercise exercise = (Exercise) o;
        return id == exercise.id && Double.compare(exercise.duration, duration) == 0 && name.equals(exercise.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, date);
    }
}
