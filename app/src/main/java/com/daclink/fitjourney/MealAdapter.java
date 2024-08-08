// com/daclink/fitjourney/MealAdapter.java
package com.daclink.fitjourney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import com.daclink.fitjourney.Database.entities.Meals;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meals> mealsList;

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView detailsTextView;

        public MealViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            detailsTextView = view.findViewById(R.id.detailsTextView);
        }
    }

    public MealAdapter(List<Meals> mealsList) {
        this.mealsList = mealsList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meals meal = mealsList.get(position);
        holder.titleTextView.setText(String.format(Locale.US, "Meal: %s", meal.getMeal()));
        holder.detailsTextView.setText(String.format(Locale.US, "Date: %s\nCalories: %.2f",
                meal.getDate(), meal.getCalories()));
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}
