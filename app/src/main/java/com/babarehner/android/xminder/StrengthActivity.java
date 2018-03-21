package com.babarehner.android.xminder;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ListView;

import com.babarehner.android.xminder.data.ExerciseContract;

/**
 * Created by mike on 6/15/17.
 */

public class StrengthActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ExerciseLoader = 1;
    StrengthCursorAdapter mCursorAdapter;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);

        setTitle("Strength Training");   // Title for page

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StrengthActivity.this, StrengthExActivity.class);
                startActivity(intent);
            }
        });

        ListView strengthListView = (ListView) findViewById(R.id.list_view_strength);
        // have the strengthListView display the empty view
        View emptyView = findViewById(R.id.empty_view);
        strengthListView.setEmptyView(emptyView);

        mCursorAdapter = new StrengthCursorAdapter(this, null);
        strengthListView.setAdapter(mCursorAdapter);

        strengthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                // create a new intent to go to {@link StrengthExActivity}
                Intent intent = new Intent(StrengthActivity.this, StrengthExActivity.class);
                // Form the content URI by appending the "id: passed to theis method into the
                // {@link StrengthEntry#CONTENTURI
                Uri currentStrengthUri = ContentUris.withAppendedId(
                        ExerciseContract.ExerciseEntry.STRENGTH_URI, id);
                intent.setData(currentStrengthUri);
                startActivity(intent);
            }
        });

        // fire up the loader
        getLoaderManager().initLoader(ExerciseLoader, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection with columns we wish to show- CursorAdapter requires "_ID" column
        String[] projection = {ExerciseContract.ExerciseEntry._IDS,
                            ExerciseContract.ExerciseEntry.C_EX_NAME,
                            ExerciseContract.ExerciseEntry.C_WEIGHT,
                            ExerciseContract.ExerciseEntry.C_REPS};

        // The loader will executer the ContentProvider's query method on a background thread
        return new CursorLoader(this, ExerciseContract.ExerciseEntry.STRENGTH_URI, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Callback for when the data is to be refreshed
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback for when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
