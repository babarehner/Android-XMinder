package com.babarehner.android.xminder;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mike on 3/13/18.
 */

public class StrengthExActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EXISTING_EXERCISE_LOADER = 0;
    private Uri mCurrentExerciseSetUri = null;
    private Uri mCurrentExerciseUri;

    private EditText mNameEditText;
    private EditText mText;
    private EditText mDurationEditText;

    private boolean mExerciseChanged = false;   // When edit change made to a book row

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

    }

    /*
        //Get intent and get data from intent
        Intent intent = getIntent();
        mCurrentBookUri = intent.getData();

        // Idf the intent does not contain a single item Uri FAB clicked
        if (mCurrentBookUri == null){
            // set page header to add book
            setTitle(getString(R.string.book_activity_title_add_book));
            // take out the delete menu
            invalidateOptionsMenu();
        } else {                            // individual item clicked
            setTitle(getString(R.string.book_activity_title_edit_book));
            getLoaderManager().initLoader(EXISTING_BOOK_LOADER, null, BookActivity.this);
        }

    */


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
