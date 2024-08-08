// com/daclink/fitjourney/ProgressAdapter.java
package com.daclink.fitjourney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;

public class ProgressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_EXERCISE = 0;
    private static final int VIEW_TYPE_MEAL = 1;

    private List<Object> progressList;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView detailsTextView;

        public ExerciseViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            detailsTextView = view.findViewById(R.id.detailsTextView);
        }
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView detailsTextView;

        public MealViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            detailsTextView = view.findViewById(R.id.detailsTextView);
        }
    }

    public ProgressAdapter(List<Object> progressList) {
        this.progressList = progressList;
    }

    @Override
    public int getItemViewType(int position) {
        if (progressList.get(position) instanceof Exercise) {
            return VIEW_TYPE_EXERCISE;
        } else {
            return VIEW_TYPE_MEAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EXERCISE) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false);
            return new ExerciseViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false);
            return new MealViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_EXERCISE) {
            Exercise exercise = (Exercise) progressList.get(position);
            ExerciseViewHolder exerciseViewHolder = (ExerciseViewHolder) holder;
            exerciseViewHolder.titleTextView.setText(String.format(Locale.US, "Exercise: %s", exercise.getName()));
            exerciseViewHolder.detailsTextView.setText(String.format(Locale.US, "Date: %s\nDuration: %.2f mins",
                    exercise.getDate(), exercise.getDuration()));
        } else {
            Meals meal = (Meals) progressList.get(position);
            MealViewHolder mealViewHolder = (MealViewHolder) holder;
            mealViewHolder.titleTextView.setText(String.format(Locale.US, "Meal: %s", meal.getMeal()));
            mealViewHolder.detailsTextView.setText(String.format(Locale.US, "Date: %s\nCalories: %.2f",
                    meal.getDate(), meal.getCalories()));
        }
    }

    @Override
    public int getItemCount() {
        return progressList.size();
    }
}
