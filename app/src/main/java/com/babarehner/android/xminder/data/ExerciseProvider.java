package com.babarehner.android.xminder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.babarehner.android.xminder.data.ExerciseContract.CONTENT_AUTHORITY;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.CARDIO_ITEM_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.CARDIO_LIST_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.STRENGTH_ITEM_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.STRENGTH_LIST_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TCARDIO;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TSTRENGTH;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry._IDS;
import static com.babarehner.android.xminder.data.ExerciseContract.PATH_TCARDIO;
import static com.babarehner.android.xminder.data.ExerciseContract.PATH_TSTRENGTH;

/**
 * Created by mike on 6/16/17.
 */

public class ExerciseProvider extends ContentProvider {

    // Tag for log messages
    public static final String LOG_TAG  = ExerciseProvider.class.getSimpleName();

    private ExerciseDBHelper mDBHelper;

    // URI matcher int for strength exercises from the TStrength table
    private static final int STRENGTH_LIST = 100;
    private static final int STRENGTH_ID = 105;
    private static final int CARDIO_LIST = 200;
    private static final int CARDIO_ID = 205;

    /**
     * URIMatcher object to match a content URI to a correpsodig int.
     * The input passed into the constructor represents the int to return for
     * the root URI
     * It's common to use NO_MATCH for this case
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // static initializer- run for the first time anything called from this class.
    static {
        /**
         * The calls to addURi() go here for all of the content URi patterns that the
         * provider should recognize. All paths added to the urimatcher should have a
         * corresponding code to return when a match is found.
         */
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TSTRENGTH, STRENGTH_LIST);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TSTRENGTH + "/#", STRENGTH_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TCARDIO, CARDIO_LIST);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TCARDIO + "/#", CARDIO_ID);
    }


    @Override
    public boolean onCreate() {
        mDBHelper = new ExerciseDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI, use the given projection, selection
     * arguments and sort order
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //Create or open DB
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor c;       //Hold the results of the query

        // determine if the URI  matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case STRENGTH_LIST:
               c = db.query(TSTRENGTH, projection, selection, selectionArgs,
                    null, null, sortOrder);
               break;
            case STRENGTH_ID:
                selection = ExerciseContract.ExerciseEntry._IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                c = db.query(TSTRENGTH, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CARDIO_LIST:
                c = db.query(TCARDIO, projection, selection, selectionArgs,
                    null, null, sortOrder);
                break;
            case CARDIO_ID:
                selection = _IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                c = db.query(TCARDIO, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(" Cannnot query with unknown URI " + uri);
        }

        // Set notification urI on the cursor so we know what content the Uri the cursor
        //was created for. If the data at this URI changes, then we need to update the cursor
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }



    // TODO implement insert
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case STRENGTH_LIST:
                return insertStrengthEx(uri, values);
            case CARDIO_LIST:
                return insertCardioEx(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertStrengthEx(Uri uri, ContentValues values){
        // Check that the exercise name is not null again
        String exName = values.getAsString(ExerciseContract.ExerciseEntry.C_EX_NAME);
        if (exName == null){
            throw new IllegalArgumentException("Exercise name required in insertStrengthEx to insert exercise");
        }

        String exWeight = values.getAsString(ExerciseContract.ExerciseEntry.C_WEIGHT);
        String exReps = values.getAsString(ExerciseContract.ExerciseEntry.C_REPS);
        String exDate = values.getAsString(ExerciseContract.ExerciseEntry.C_DATE);
        String exNote = values.getAsString(ExerciseContract.ExerciseEntry.C_NOTES);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        // insert an exercise into the database
        long id = db.insert(ExerciseContract.ExerciseEntry.TSTRENGTH, null, values);
        // TODO follow the log tag below in logcat
        Log.v(LOG_TAG, "Exercise not entered " + values);
        if (id == -1){
           Log.e(LOG_TAG, "Failed to insert row for " + uri);
           return null;
        }

        // notify all listeners that the data has changed for the TSTRENGTH content uri
        getContext().getContentResolver().notifyChange(uri, null);
        // return the new URI with the ID of the newly inserted row appended to the db
        return ContentUris.withAppendedId(uri,id);

    }


    private Uri insertCardioEx(Uri uri, ContentValues values) {
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STRENGTH_LIST:
                return updateStrengthEx(uri, values, selection, selectionArgs);
            case STRENGTH_ID:
                selection = ExerciseContract.ExerciseEntry._IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateStrengthEx(uri, values, selection, selectionArgs);
            case CARDIO_LIST:
                return updateCardioEx(uri, values, selection, selectionArgs);
            case CARDIO_ID:
                selection = ExerciseContract.ExerciseEntry._IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateCardioEx(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for: " + uri);
        }
    }


    private int updateStrengthEx(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        // if there are no values quit
        if (values.size() == 0) {return 0;}

        // check that the exercise name value is not empty
        if (values.containsKey(ExerciseContract.ExerciseEntry.C_EX_NAME)) {
            String exName = values.getAsString(ExerciseContract.ExerciseEntry.C_EX_NAME);
            // check again
            if (exName == null){
                throw new IllegalArgumentException("Exercise requires a name in updatStrengthEx");
            }
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowsUpdated = db.update(ExerciseContract.ExerciseEntry.TSTRENGTH, values, selection,
                selectionArgs);
        // notify all listeners that the db has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    private int updateCardioEx(Uri uri, ContentValues values, String selction, String[] selectionArgs){
        return 0;
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch(match){
            case STRENGTH_LIST:
                // Delete all rows that match the selection and selection args
                rowsDeleted = db.delete(ExerciseContract.ExerciseEntry.TSTRENGTH, selection, selectionArgs);
                break;
            case STRENGTH_ID:
                // Delete a single row fiven by the ID in the URI
                selection = ExerciseContract.ExerciseEntry._IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ExerciseContract.ExerciseEntry.TSTRENGTH, selection, selectionArgs);
                break;
            case CARDIO_LIST:
                rowsDeleted = db.delete(ExerciseContract.ExerciseEntry.TCARDIO, selection, selectionArgs);
                break;
            case CARDIO_ID:
                // Delete a single row fiven by the ID in the URI
                // selection = ExerciseContract.ExerciseEntry._IDC + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ExerciseContract.ExerciseEntry.TCARDIO, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for: " + uri);
        }

        if (rowsDeleted != 0){
            // Notify all listeners that the date at the given Uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STRENGTH_LIST:
                return STRENGTH_LIST_TYPE;
            case STRENGTH_ID:
                return STRENGTH_ITEM_TYPE;
            case CARDIO_LIST:
                return CARDIO_LIST_TYPE;
            case CARDIO_ID:
                return CARDIO_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match = " + match);
        }
    }
}
