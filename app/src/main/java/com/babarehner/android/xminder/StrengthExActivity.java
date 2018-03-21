package com.babarehner.android.xminder;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.babarehner.android.xminder.data.ExerciseContract;

import java.util.Calendar;

/**
 * Created by mike on 3/13/18.
 */

public class StrengthExActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EXISTING_EXERCISE_LOADER = 9;

    private Uri mCurrentStrengthExUri;

    private EditText mExNameEditText;
    private EditText mWeightEditText;
    private EditText mRepsEditText;
    private EditText mNotesEditText;

    private TextView tvDate;
    private Button pickDate;
    private int mYear, mMonth, mDay;

    static final int DATE_DIALOG_ID = 99;

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
        // String str = mCurrentStrengthExUri.toString();

        // If the intent does not contain a single item-  Uri FAB clicked
        if (mCurrentStrengthExUri == null) {
            // set page header to add exercise
            setTitle(getString(R.string.strengthex_activity_title_add_exercise));
            // take out the delete menu
            invalidateOptionsMenu();
        } else {
            // set page header to edit exercise
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

        getDate();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {ExerciseContract.ExerciseEntry._IDS,
                ExerciseContract.ExerciseEntry.C_EX_NAME,
                ExerciseContract.ExerciseEntry.C_WEIGHT,
                ExerciseContract.ExerciseEntry.C_REPS,
                ExerciseContract.ExerciseEntry.C_DATE,
                ExerciseContract.ExerciseEntry.C_NOTES };
        // start a new thread
        return new CursorLoader(this, mCurrentStrengthExUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        // move to the only row in the cursor
        if (c.moveToFirst()) {
            int exNameColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_EX_NAME);
            int exWeightColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_WEIGHT);
            int exRepsColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_REPS);
            int exDateColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_DATE);
            int exNoteColIndex = c.getColumnIndex(ExerciseContract.ExerciseEntry.C_NOTES);

            // use index to pull data out of the cursor
            String exName = c.getString(exNameColIndex);
            String exWeight = c.getString(exWeightColIndex);
            String exReps = c.getString(exRepsColIndex);
            String exDate = c.getString(exDateColIndex);
            String exNote = c.getString(exNoteColIndex);

            // update the textviews
            mExNameEditText.setText(exName);
            mWeightEditText.setText(exWeight);
            mRepsEditText.setText(exReps);
            mNotesEditText.setText(exNote);
            tvDate.setText(exDate);
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

    // set up date picker
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

    // Dialog needed to launch date picker called by showDialog()
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this, DateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    //TODO change save checkmart to white color
    @Override   // show the options menu
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_edit, m);
        return true;
    }

    @Override   // hide delete menu item when adding a new exercise
    public boolean onPrepareOptionsMenu(Menu m) {
        super.onPrepareOptionsMenu(m);
        // if this is add an exercise, hide "delete" menu item
        if (mCurrentStrengthExUri == null) {
            MenuItem menuItem = m.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }



    @Override        // Select from the options menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveExercise();
                finish();       // exit activity
                return true;
            case R.id.action_delete:
                // Alert Dialog for deleting one book
                showDeleteConfirmationDialog();
                return true;
            // this is the <- button on the header
            case android.R.id.home:
                // book has not changed
                if (!mExerciseChanged) {
                    NavUtils.navigateUpFromSameTask(StrengthExActivity.this);
                    return true;
                }
                // set up dialog to warn user about unsaved changes
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //user click discard. Navigate up to parent activity
                                NavUtils.navigateUpFromSameTask(StrengthExActivity.this);
                            }
                        };
                // show user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveExercise() {

        // read from EditText input fields
        String exNameString = mExNameEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        String repsString = mRepsEditText.getText().toString().trim();
        String notesString = mNotesEditText.getText().toString().trim();
        String dateString = tvDate.getText().toString().trim();


        // if adding exercise and the name field is left blank do nothing
        if (mCurrentStrengthExUri == null && TextUtils.isEmpty(exNameString)) {
            Toast.makeText(this, getString(R.string.missing_exercise_name),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ExerciseContract.ExerciseEntry.C_EX_NAME, exNameString);
        values.put(ExerciseContract.ExerciseEntry.C_WEIGHT, weightString);
        values.put(ExerciseContract.ExerciseEntry.C_REPS, repsString);
        values.put(ExerciseContract.ExerciseEntry.C_NOTES, notesString);
        values.put(ExerciseContract.ExerciseEntry.C_DATE, dateString);

        if (mCurrentStrengthExUri == null) {
            // a new exercise
            Uri newUri = getContentResolver().insert(ExerciseContract.ExerciseEntry.STRENGTH_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.strengthex_insert_exercise_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.strengthex_insert_exercise_good),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // existing book so update with content URI and pass in ContentValues
            int rowsAffected = getContentResolver().update(mCurrentStrengthExUri, values, null, null);
            if (rowsAffected == 0) {
                // TODO Check db- Text Not Null does not seem to be working or entering
                // "" does not mean NOT Null- there must be an error message closer to the db!!!
                Toast.makeText(this, getString(R.string.edit_update_strengthex_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.edit_update_strengthex_success),
                        Toast.LENGTH_SHORT).show();
            }

        }
    }


    // delete exercise from DB
    private void deleteExercise(){
        if (mCurrentStrengthExUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentStrengthExUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_exercise_failure),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_exercise_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder, set message and click
        // listeners for positive and negative buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked delet so delete
                deleteExercise();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked cancel, dismiss dialog, continue editing
                if (dialog != null) {dialog.dismiss();}
            }
        });
        // Create and show dialog
        AlertDialog alertD = builder.create();
        alertD.show();
    }


    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener){
        // Create AlertDialogue.Builder amd set message and click listeners
        // for positive and negative buttons in dialogue.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // user clicked the "keep eiditing" button. Dismiss dialog and keep editing
                if (dialog !=null) { dialog.dismiss();}
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // Override the activity's normal back button. If book has changed create a
    // discard click listener that closed current activity.
    @Override
    public void onBackPressed() {
        if (!mExerciseChanged) {
            super.onBackPressed();
            return;
        }
        //otherwise if there are unsaved changes setup a dialog to warn the  user
        //handles the user confirming that changes should be made
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // user clicked "Discard" button, close the current activity
                        finish();
                    }
                };
        // show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

}
