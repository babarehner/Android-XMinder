package com.babarehner.android.xminder;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ListView;

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
        /*
        mCursorAdapter = new StrengthCursorAdapter(this, null);
        strengthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClickListener(AdapterView<?> parent, View v, int post, long id) {
                // create a new intent to go to {@link StrengthExActivity}
                Intent intent = new Intent(StrengthActivity.this, StrengthExActivity.class);

                // Form the content URI by appending the "id: passed to theis method into the
                // {@link StrengthEntry#CONTENTURI
                Uri currentStrengthUri = Content
            }
        };
        */

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return null;
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
