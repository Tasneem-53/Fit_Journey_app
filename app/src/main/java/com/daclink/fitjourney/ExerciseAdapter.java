// com/daclink/fitjourney/ExerciseAdapter.java
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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView detailsTextView;

        public ExerciseViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            detailsTextView = view.findViewById(R.id.detailsTextView);
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.titleTextView.setText(String.format(Locale.US, "Exercise: %s", exercise.getName()));
        holder.detailsTextView.setText(String.format(Locale.US, "Date: %s\nDuration: %.2f minutes",
                exercise.getDate(), exercise.getDuration()));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
