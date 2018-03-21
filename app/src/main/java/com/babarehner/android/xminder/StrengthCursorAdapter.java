package com.babarehner.android.xminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.babarehner.android.xminder.data.ExerciseContract;

/**
 * Created by mike on 6/15/17.
 */

public class StrengthCursorAdapter extends CursorAdapter {

    public StrengthCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Creates a new blank list item with not data
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_strength, parent, false);
    }

    /**
     * Binds data to the empty list item created above
     * @param v
     * @param context
     * @param c
     */
    @Override
    public void bindView(View v, Context context, Cursor c) {
        // find the id of the views to modify
        TextView exNameTextView = (TextView) v.findViewById(R.id.exercise_name);
        TextView effortTextView = (TextView) v.findViewById(R.id.effort_text_view);
        TextView durationTextView = (TextView) v.findViewById(R.id.duration_text_view);

        int exNameColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_EX_NAME);
        int exWeightColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_WEIGHT);
        int exRepsColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_REPS);

        String exName = c.getString(exNameColIndex);
        String exWeight = c.getString(exWeightColIndex);
        String exReps = c.getString(exRepsColIndex);

        exNameTextView.setText(exName);
        effortTextView.setText(exWeight);
        durationTextView.setText(exReps);



    }
}
