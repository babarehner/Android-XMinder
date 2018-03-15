package com.babarehner.android.xminder;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by mike on 3/13/18.
 */

public class StrengthExActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EXISTING_EXERCISE_LOADER = 0;
    private Uri mCurrentStrengthUri = null;
    private Uri mCurrentStrengthExUri;

    private EditText mExNameEditText;
    private EditText mWeightEditText;
    private EditText mRepsEditText;
    private EditText mNotesEditText;

    private TextView tvDate;
    private Button pickDate;
    private int mYear, mMonth, mDay;

    static final int DATE_DIALOG_ID = 0;

    private boolean mExerciseChanged = false;   // When edit change made to an exercise row

    // Touch Listener to check if changes made to a book
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mExerciseChanged = true;
            return false;
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strengthex);

        //Get intent and get data from intent
        Intent intent = getIntent();
        mCurrentStrengthExUri = intent.getData();

        // If the intent does not contain a single item Uri FAB clicked
        if (mCurrentStrengthExUri == null) {
            // set page header to add exercise
            setTitle(getString(R.string.strengthex_activity_title_add_exercise));
            // take out the delete menu
            invalidateOptionsMenu();
        } else {                            // set page header to edit exercise
            setTitle(getString(R.string.strengthex_activity_title_edit_exercise));
            getLoaderManager().initLoader(EXISTING_EXERCISE_LOADER, null, StrengthExActivity.this);
        }

        // initialization required or it crashes
        tvDate = (TextView) findViewById(R.id.tvDate);

        // Find all input views to read from
        mExNameEditText = (EditText) findViewById(R.id.edit_ex_name);
        mWeightEditText = (EditText) findViewById(R.id.edit_weight_amnt);
        mRepsEditText = (EditText) findViewById(R.id.edit_reps_num);
        mNotesEditText = (EditText) findViewById(R.id.edit_notes);

        // Set up Touch Listener on all input fields to see if a field has been modified
        mExNameEditText.setOnTouchListener(mTouchListener);
        mWeightEditText.setOnTouchListener(mTouchListener);
        mRepsEditText.setOnTouchListener(mTouchListener);
        mNotesEditText.setOnTouchListener(mTouchListener);
        tvDate.setOnTouchListener(mTouchListener);

        getDate();      // use date picker and display date as text
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()){
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If invalid Loader clear data from input field
        mExNameEditText.setText("");
        mWeightEditText.setText("");
        mRepsEditText.setText("");
        mNotesEditText.setText("");
        tvDate.setText("");
    }

    // get up date picker
    public void getDate() {

        tvDate = (TextView) findViewById(R.id.tvDate);
        pickDate = (Button) findViewById(R.id.pick_date);

        // add a click listener to the button
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        //get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }



    //updates the date displayed in TextView
    private void updateDate() {
        tvDate.setText(
                new StringBuilder()
                        .append((mMonth + 1)).append("/")
                        .append(mDay).append("/")
                        .append(mYear).append(" "));
    }

    // the callback received when the user sets the date in the dialog
    private DatePickerDialog.OnDateSetListener DateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;
                    updateDate();
                }
            };

}
